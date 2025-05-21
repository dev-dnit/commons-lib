package dnit.commons.api


/**
 * Classe para armazenar informações adicionais sobre a resposta da API
 *
 * @property type Tipo de informação, geralmente utilizando em Toast (ex: success, erro, etc.)
 * @property message Mensagem adicional
 */
data class InfoResult(

    val type : InfoType = InfoType.NORMAL,
    val message : String? = null,

)



/**
 * Enumeração para tipos de informações adicionais
 */
enum class InfoType {

    SUCCESS,
    INFO,
    ERROR,
    WARNING,
    NORMAL

}
