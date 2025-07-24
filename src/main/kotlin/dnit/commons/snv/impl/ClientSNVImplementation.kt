package dnit.commons.snv.impl

import dnit.commons.exception.CommonException
import dnit.commons.geo.ValidadorCoordenadas
import dnit.commons.model.internal.MiniTrechoSNV
import dnit.commons.model.internal.RotaSNV
import dnit.commons.snv.SNVResponse
import dnit.commons.utils.runSuspendableWithRetry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


/**
 * Classe cliente para acessar os SNVs disponíveis.
 */
internal object ClientSNVImplementation {

    @Volatile
    private var delayClusterDays: Long? = null // Existe um erro de data no cluster
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val cacheMiniTrechos: CacheMiniTrechos = CacheMiniTrechos(maxItems = 10)



    suspend fun obtemVersaoSnvAtual(): String? {
        val dataAtual = formataData(null)

        return obtemVersaoSnv(dataAtual)
    }




    suspend fun obtemVersaoSnv(dataReferencia: String): String? {
        val rotas = runSuspendableWithRetry(
            callable = { ApiGeoClientImpl.fetchRota(-16.621117,-49.207783, 10_000.0, dataReferencia) },
            maxRetries = 3,
            delayMs = 1_000L,
            defaultValue = emptyList(),
        )
        return rotas.firstOrNull()?.versao
    }




    suspend fun obtemSnvs(
        lat : Double,
        lng : Double,
        uf : String?,
        br : String?,
        dataReferencia: String?,
        startBuffer: Double,
        maxBuffer: Double,
        retryCount: Int,
        retryDelayMs: Long,
    ): List<SNVResponse> {
        val data = formataData(dataReferencia)
        val (buffer, rotas) = obtemRotasSnv(lat, lng, data,
                                  startBuffer, maxBuffer,
                                  retryCount, retryDelayMs)

        return rotas.flatMap { rota ->
            toSnvResponse(
                lat,
                lng,
                rota,
                buffer / 1000.0,
                data,
                retryCount,
                retryDelayMs
            )
        }
        .sortedWith(compareBy<SNVResponse> { it.uf != uf }
            .thenBy { it.br != br }
            .thenBy { it.snv == null }
            .thenBy { it.snv }
            .thenBy { it.uf }
            .thenBy { it.br }
            .thenBy { it.tipo }
        )
    }


    private suspend fun obtemRotasSnv(
        lat : Double,
        lng : Double,
        dataReferencia : String,
        startBuffer: Double,
        maxBuffer: Double,
        retryCount: Int,
        retryDelayMs: Long,
    ): Pair<Double, List<RotaSNV>> {

        var usedBuffer = 0.0
        require(dataReferencia.isNotBlank()) { "Data referencia deve ser preenchida"}
        require(startBuffer > 0) { "Buffer inicial deve ser positivo" }
        require(startBuffer <= maxBuffer) { "Buffer max deve ser maior ou igual ao buffer inicial" }
        require(retryCount >= 0) { "Número de tentativas deve ser maior ou igual a zero" }
        require(retryDelayMs >= 0) { "RetryDelay deve ser maior ou igual a zero" }

        for (buffer in generateBufferSequence(startBuffer, maxBuffer, retryCount)) {
            usedBuffer = buffer
            val result = runSuspendableWithRetry(
                callable = { ApiGeoClientImpl.fetchRota(lat, lng, buffer, dataReferencia) },
                maxRetries = retryCount,
                delayMs = retryDelayMs,
                defaultValue = emptyList(),
            )

            if (result.isNotEmpty()) {
                return Pair(usedBuffer, result)
            }
        }

        return Pair(usedBuffer, emptyList())
    }




    private suspend fun toSnvResponse(
        lat : Double,
        lng : Double,
        rota : RotaSNV,
        bufferKm: Double,
        dataReferencia : String,
        retryCount : Int,
        retryDelayMs : Long,
    ) : List<SNVResponse> {
        var kmAcumulado = 0.0
        val listaResponse = mutableListOf<SNVResponse>()

        val trechoSnv : List<MiniTrechoSNV> =
            cacheMiniTrechos.obtemCacheOrFetch(rota.uf, rota.br, rota.sgTpTrecho, dataReferencia
            ) {
                if ("B" != rota.sgTpTrecho) return@obtemCacheOrFetch emptyList()
                runSuspendableWithRetry(
                    callable = {
                        ApiGeoClientImpl.fetchTrecho(
                            rota.uf,
                            rota.br,
                            rota.sgTpTrecho,
                            dataReferencia
                        )
                    },
                    maxRetries = retryCount,
                    delayMs = retryDelayMs,
                    defaultValue = emptyList()
                )
            }

        for (trecho in trechoSnv) {
            val inicioTrecho = kmAcumulado
            kmAcumulado += trecho.extensao // final
            if (rota.km >= inicioTrecho - bufferKm && rota.km <= kmAcumulado + bufferKm) {
                listaResponse.add(SNVResponse(
                    snv = trecho.trecho,
                    versao = rota.versao,
                    coincidencia = trecho.coincidencia,
                    uf = rota.uf,
                    br = rota.br,
                    tipo = rota.sgTpTrecho,
                    latitude = lat,
                    longitude = lng,
                    rota.km
                ))
            }
        }

        if (listaResponse.isNotEmpty()) {
            return listaResponse
        }

        // Tenta obter o ultimo trecho
        trechoSnv.lastOrNull()?.let {
            val toleranciaExcedente = 10.0
            if (kmAcumulado + toleranciaExcedente >= rota.km) {
                return mutableListOf(SNVResponse(
                    snv = it.trecho,
                    versao = rota.versao,
                    coincidencia = it.coincidencia,
                    uf = rota.uf,
                    br = rota.br,
                    tipo = rota.sgTpTrecho,
                    latitude = lat,
                    longitude = lng,
                    km = rota.km
                ))
            }
        }

        // Fallback - Retorna um trecho vazio
        return mutableListOf(SNVResponse(
            snv = null,
            versao = rota.versao,
            coincidencia = null,
            uf = rota.uf,
            br = rota.br,
            tipo = rota.sgTpTrecho,
            latitude = lat,
            longitude = lng,
            km = rota.km,
        ))
    }




    private fun generateBufferSequence(
        startBuffer : Double,
        maxBuffer : Double,
        retryCount : Int,
    ): List<Double> {
        return when {
            retryCount <= 1 -> listOf(startBuffer)
            startBuffer == maxBuffer -> List(retryCount) { startBuffer }
            else -> (0 until retryCount).map { i ->
                startBuffer + (maxBuffer - startBuffer) * i / (retryCount - 1)
            }
        }
    }




    /**
     * Converte uma String para o formato padrão. Se for nulo, obtém a data atual.
     */
    private fun formataData(dataReferencia: String?): String {
        if (dataReferencia.isNullOrBlank()) {
            // Retorna a data atual
            return formatter.format(LocalDate.now().minusDays(delayClusterDays ?: 0))
        }

        return try {
            LocalDate.parse(dataReferencia, formatter) // Validates the data format
            dataReferencia // Return original if valid

        } catch (exception: DateTimeParseException) {
            throw CommonException("Formato data invalida: '$dataReferencia' Deve ser em yyyy-MM-dd",
                                   exception)
        }
    }


}
