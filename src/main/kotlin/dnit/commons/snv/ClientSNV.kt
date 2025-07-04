package dnit.commons.snv

import dnit.commons.snv.impl.ClientSNVImplementation
import kotlinx.coroutines.runBlocking


/**
 * Classe cliente para acessar os SNVs disponíveis.
 */
object ClientSNV {



    /**
     * Retorna a versão do SNV para a data atual
     * @return Versão do SNV encontrada ou null se não encontrada
     */
    @JvmStatic
    fun obtemVersaoSnvAtualBlocking() : String? = runBlocking {
        obtemVersaoSnvAtual()
    }




    /**
     * Retorna a versão do SNV para a data atual
     * @return Versão do SNV encontrada ou null se não encontrada
     */
    suspend fun obtemVersaoSnvAtual() : String? {
        return ClientSNVImplementation.obtemVersaoSnvAtual()
    }




    /**
     * Retorna a versão do SNV para a data de referência especificada.
     * @param dataReferencia Data a ser consultada, no formato 'yyyy-MM-dd'
     * @return Versão do SNV encontrada ou null se não encontrada
     */
    @JvmStatic
    fun obtemVersaoSnvBlocking(dataReferencia: String) : String? = runBlocking {
        ClientSNVImplementation.obtemVersaoSnv(dataReferencia)
    }




    /**
     * Retorna a versão do SNV para a data de referência especificada.
     * @param dataReferencia Data a ser consultada, no formato 'yyyy-MM-dd'
     * @return Versão do SNV encontrada ou null se não encontrada
     */
    suspend fun obtemVersaoSnv(dataReferencia: String) : String? {
        return ClientSNVImplementation.obtemVersaoSnv(dataReferencia)
    }




    /**
     * Retorna a lista de snvs próximo a coordenada informada
     */
    @JvmStatic
    @JvmOverloads
    fun obtemSNVsBlocking(
        latitude : Double,
        longitude : Double,
        dataReferencia: String? = null, // formato 'yyyy-MM-dd' (se nulo, é utilizado data de hoje)
        uf : String? = null,
        br : String? = null,
        startBuffer: Double = 150.0,
        maxBuffer: Double = 1_500.0,
        retryCount: Int = 4,
        retryDelay: Long = 1_000L,
    ) : List<SNVResponse> = runBlocking {
        ClientSNVImplementation.obtemSnvs(
            lat = latitude,
            lng = longitude,
            uf = uf,
            br = br,
            dataReferencia = dataReferencia,
            startBuffer = startBuffer,
            maxBuffer = maxBuffer,
            retryCount = retryCount,
            retryDelayMs = retryDelay,
        )
    }




    /**
     * Retorna a lista de snvs próximo a coordenada informada
     */
    suspend fun obtemSNVs(
        latitude : Double,
        longitude : Double,
        dataReferencia: String? = null, // formato 'yyyy-MM-dd' (se nulo, é utilizado data de hoje)
        uf : String? = null,
        br : String? = null,
        startBuffer: Double = 150.0,
        maxBuffer: Double = 1_500.0,
        retryCount: Int = 4,
        retryDelay: Long = 1_000L,
    ) : List<SNVResponse> {
        return ClientSNVImplementation.obtemSnvs(
            lat = latitude,
            lng = longitude,
            uf = uf,
            br = br,
            dataReferencia = dataReferencia,
            startBuffer = startBuffer,
            maxBuffer = maxBuffer,
            retryCount = retryCount,
            retryDelayMs = retryDelay,
        )
    }


}
