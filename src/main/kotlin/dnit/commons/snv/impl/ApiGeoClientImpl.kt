package dnit.commons.snv.impl

import dnit.commons.exception.CommonException
import dnit.commons.model.internal.MiniTrechoSNV
import dnit.commons.model.internal.RotaSNV
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URLEncoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Client responsável por se comunicar com a API do VGEO e retornar os dados de SNV
 * Essa
 */
internal object ApiGeoClientImpl {

    private val baseUrlRotas = "https://servicos.dnit.gov.br/sgplan/apigeo/rotas/localizarkm"
    private val baseUrlSnvs = "https://servicos.dnit.gov.br/sgplan/apigeo/snv/trechos"


    internal suspend fun fetchTrecho(
        uf: String,
        br: String,
        tipo: String,
        dataReferencia: String,
        connectionTimeoutMs: Int = 15_000,
        readTimeoutMs: Int = 30_000,
    ): List<MiniTrechoSNV> = withContext(Dispatchers.IO) {
        // Build query parameters
        val params = mapOf(
            "uf" to uf,
            "br" to br,
            "tipo" to tipo,
            "data" to URLEncoder.encode(dataReferencia, "UTF-8"),
        )

        val queryString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        val fullSnvUrl = "$baseUrlSnvs?$queryString"

        try {
            val url = URI(fullSnvUrl).toURL()
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Content-Type", "application/json")
                connectTimeout = connectionTimeoutMs
                readTimeout = readTimeoutMs
            }

            val responseCode = connection.responseCode

            when (responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

                    // Parse JSON response manually
                    parseTrechoResponse(response)
                }

                HttpURLConnection.HTTP_NOT_FOUND -> emptyList()

                else -> throw CommonException(
                    "Erro ao obter trechos: $responseCode - ${connection.responseMessage} - $fullSnvUrl"
                )
            }

        } catch (e: Exception) {
            if (e is CommonException) throw e
            throw CommonException("Erro ao obter trechos SNV", e)
        }
    }



    internal suspend fun fetchRota(
        lat: Double,
        lng: Double,
        bufferM: Double,
        dataReferencia: String,
        connectionTimeoutMs: Int = 15_000,
        readTimeoutMs: Int = 30_000,
    ): List<RotaSNV> = withContext(Dispatchers.IO) {
        val params = mapOf(
            "lng" to lng.toString(),
            "lat" to lat.toString(),
            "r" to bufferM.toString(),
            "data" to URLEncoder.encode(dataReferencia, "UTF-8"),
        )

        val queryString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        val fullUrl = "$baseUrlRotas?$queryString"

        try {
            val url = URI(fullUrl).toURL()
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Content-Type", "application/json")
                connectTimeout = connectionTimeoutMs
                readTimeout = readTimeoutMs
            }

            val responseCode = connection.responseCode

            when (responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    parseRotaResponse(response)
                }

                HttpURLConnection.HTTP_NOT_FOUND -> emptyList()

                else -> throw CommonException("Erro ao obter rotas: $responseCode - ${connection.responseMessage} - $fullUrl")
            }

        } catch (e: Exception) {
            if (e is CommonException) throw e
            throw CommonException("Erro ao obter SNV", e)
        }
    }


    private fun parseTrechoResponse(jsonResponse : String) : List<MiniTrechoSNV> {
        // Simple JSON parsing without external libraries
        val content = jsonResponse.trim().removeSurrounding("{", "}")
        val fields = mutableMapOf<String, String>()

        // Extract main fields from the response
        var i = 0
        while (i < content.length) {
            // Find field name
            val keyStart = content.indexOf("\"", i)
            if (keyStart == -1) break
            val keyEnd = content.indexOf("\"", keyStart + 1)
            if (keyEnd == -1) break

            val key = content.substring(keyStart + 1, keyEnd)

            // Find colon
            val colonIndex = content.indexOf(":", keyEnd)
            if (colonIndex == -1) break

            // Determine value based on type
            val valueStart = colonIndex + 1
            var valueEnd: Int
            val value: String

            // Skip whitespace
            var pos = valueStart
            while (pos < content.length && content[pos].isWhitespace()) pos++

            if (pos >= content.length) break

            when (content[pos]) {
                '"' -> {
                    // String value
                    valueEnd = content.indexOf("\"", pos + 1)
                    if (valueEnd == -1) break
                    value = content.substring(pos + 1, valueEnd)
                    i = valueEnd + 1
                }
                '[' -> {
                    // Array value - find matching closing bracket
                    var bracketCount = 1
                    var arrayPos = pos + 1
                    while (arrayPos < content.length && bracketCount > 0) {
                        when (content[arrayPos]) {
                            '[' -> bracketCount++
                            ']' -> bracketCount--
                        }
                        arrayPos++
                    }
                    value = content.substring(pos, arrayPos)
                    i = arrayPos
                }
                else -> {
                    // Number or other value
                    valueEnd = content.indexOfAny(charArrayOf(',', '}'), pos)
                    if (valueEnd == -1) valueEnd = content.length
                    value = content.substring(pos, valueEnd).trim()
                    i = valueEnd
                }
            }

            fields[key] = value

            // Skip comma
            while (i < content.length && (content[i] == ',' || content[i].isWhitespace())) {
                i++
            }
        }

        // Extract trechos array and parse it
        val trechosJson = fields["trechos"] ?: return emptyList()
        return parseTrechosArray(trechosJson)
    }



    private fun parseRotaResponse(jsonResponse : String) : List<RotaSNV> {
        // Simple JSON parsing without external libraries
        // It's done like this to avoid importing an external library and having conflicts
        if (jsonResponse.trim().startsWith("[")) {
            // Response is an array
            return parseJsonArray(jsonResponse)
        } else if (jsonResponse.trim().startsWith("{")) {
            // Response is a single object
            val rota = parseJsonObject(jsonResponse)
            return if (rota != null) listOf(rota) else emptyList()
        }

        return emptyList()
    }


    private fun parseTrechosArray(trechosJson: String): List<MiniTrechoSNV> {
        val trechos = mutableListOf<MiniTrechoSNV>()

        // Remove outer brackets
        val content = trechosJson.trim().removeSurrounding("[", "]")
        if (content.isBlank()) return emptyList()

        // Split objects in the array
        val objects = splitTrechoObjects(content)

        objects.forEach { objStr ->
            parseTrechoObject("{$objStr}")?.let { trechos.add(it) }
        }

        return trechos
    }



    private fun parseJsonArray(jsonArray : String) : List<RotaSNV> {
        val rotaSNVS = mutableListOf<RotaSNV>()

        // Remove outer brackets and split by objects
        val content = jsonArray.trim().removeSurrounding("[", "]")

        if (content.isBlank()) return emptyList()

        // Simple object splitting (assuming no nested objects with commas)
        val objects = splitJsonObjects(content)

        objects.forEach { objStr ->
            parseJsonObject("{$objStr}")?.let { rotaSNVS.add(it) }
        }

        return rotaSNVS
    }



    private fun splitJsonObjects(content : String) : List<String> {
        val objects = mutableListOf<String>()
        var braceCount = 0
        var start = 0

        for (i in content.indices) {
            when (content[i]) {
                '{' -> braceCount++
                '}' -> {
                    braceCount--
                    if (braceCount == 0) {
                        objects.add(
                            content.substring(start, i + 1).removePrefix("{").removeSuffix("}")
                        )
                        start = i + 1
                        // Skip comma and whitespace
                        while (start < content.length && (content[start] == ',' || content[start].isWhitespace())) {
                            start++
                        }
                    }
                }
            }
        }

        return objects
    }

    private fun splitTrechoObjects(content: String): List<String> {
        val objects = mutableListOf<String>()
        var braceCount = 0
        var start = 0

        for (i in content.indices) {
            when (content[i]) {
                '{' -> braceCount++
                '}' -> {
                    braceCount--
                    if (braceCount == 0) {
                        objects.add(
                            content.substring(start, i + 1).removePrefix("{").removeSuffix("}")
                        )
                        start = i + 1
                        // Skip comma and whitespace
                        while (start < content.length && (content[start] == ',' || content[start].isWhitespace())) {
                            start++
                        }
                    }
                }
            }
        }

        return objects
    }

    private fun parseTrechoObject(jsonObject: String): MiniTrechoSNV? {
        val content = jsonObject.trim().removeSurrounding("{", "}")
        val fields = mutableMapOf<String, String>()

        // Simple field extraction
        val pairs = content.split(",")

        pairs.forEach { pair ->
            val colonIndex = pair.indexOf(":")
            if (colonIndex != -1) {
                val key = pair.substring(0, colonIndex).trim().removeSurrounding("\"")
                val value = pair.substring(colonIndex + 1).trim().removeSurrounding("\"")
                fields[key] = value
            }
        }

        val trecho = fields["trecho"] ?: return null
        val extensaoStr = fields["extensao"] ?: return null
        val extensao = extensaoStr.toDoubleOrNull() ?: return null
        val coincidencia = fields["coincidencia"]?.takeIf { it != "null" }

        return MiniTrechoSNV(
            trecho = trecho,
            extensao = extensao,
            coincidencia = coincidencia
        )
    }


    private fun parseJsonObject(jsonObject : String) : RotaSNV? {
        val content = jsonObject.trim().removeSurrounding("{", "}")
        val fields = mutableMapOf<String, String>()

        // Simple field extraction
        val pairs = content.split(",")

        pairs.forEach { pair ->
            val colonIndex = pair.indexOf(":")
            if (colonIndex != -1) {
                val key = pair.substring(0, colonIndex).trim().removeSurrounding("\"")
                val value = pair.substring(colonIndex + 1).trim().removeSurrounding("\"")
                fields[key] = value
            }
        }

        return RotaSNV(
            id = fields["id"],
            idTrecho = fields["idTrecho"],
            br = fields["br"] ?: "",
            uf = fields["uf"] ?: "",
            km = fields["km"]?.toDoubleOrNull() ?: -1.0,
            lat = fields["lat"],
            lng = fields["lng"],
            versao = fields["versao"],
            sgTpTrecho = fields["sg_tp_trecho"] ?: "B",
        )

    }

}