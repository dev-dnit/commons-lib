package dnit.commons.api


/**
 * Api Result
 * Classe padrão para retorno de resultados de APIs
 *
 * @param T Tipo de dado retornado no campo [result]
 * @property result Resultado principal da requisição
 * @property info Informações adicionais sobre a resposta, como mensagens ou status
 * @property stackTrace Exceção ou erro que ocorreu durante o processamento da requisição
 * @property pagination Metadados de paginação, quando aplicável
 */
data class ApiResult<T>(

    val result : T? = null,
    val info : InfoResult? = null,
    val pagination : PageMetadata? = null,
    val stackTrace : Throwable? = null,

) {
    var validation : T? = null

    companion object {

        @JvmStatic
        fun <T> success(
            result : T? = null
        ) = ApiResult(
            result = result,
            info = InfoResult(InfoType.SUCCESS, null)
        )


        @JvmStatic
        fun <T> success(
            result : T? = null,
            message : String? = null
        ) = ApiResult(
            result = result,
            info = InfoResult(InfoType.SUCCESS, message)
        )


        @JvmStatic
        fun <T> success(
            result : T? = null,
            pagination : PageMetadata? = null
        ) = ApiResult(
            result = result,
            pagination = pagination,
            info = InfoResult(InfoType.SUCCESS, null)
        )


        @JvmStatic
        fun <T> success(
            result : T? = null,
            message : String? = null,
            pagination : PageMetadata? = null,
        ) = ApiResult(
            result = result,
            pagination = pagination,
            info = InfoResult(InfoType.SUCCESS, message),
        )


        @JvmStatic
        @JvmOverloads
        fun <T> info(
            result : T? = null,
            message : String? = null,
            pagination : PageMetadata? = null,
        ) = ApiResult(
            result = result,
            info = InfoResult(InfoType.INFO, message),
            pagination = pagination
        )


        @JvmStatic
        fun <T> error(
            message : String? = null
        ) = ApiResult(
            result = null,
            info = InfoResult(InfoType.ERROR, message)
        )


        @JvmStatic
        fun <T> validationError(
            message: String? = null,
            validations: T? = null,
        ): ApiResult<T> {
            return ApiResult<T>(
                result = null,
                info = InfoResult(InfoType.ERROR, message)
            ).apply {
                validation = validations
            }
        }


        @JvmStatic
        fun <T> error(
            message : String? = null,
            stackTrace : Throwable? = null,
        ) = ApiResult(
            result = null,
            info = InfoResult(InfoType.ERROR, message),
            stackTrace = stackTrace
        )


        @JvmStatic
        fun <T> warn(
            message : String? = null
        ) = ApiResult(
            result = null,
            info = InfoResult(InfoType.WARNING, message)
        )


        @JvmStatic
        fun <T> warn(
            message : String? = null,
            stackTrace : Throwable? = null,
        ) = ApiResult(
            result = null,
            info = InfoResult(InfoType.WARNING, message),
            stackTrace = stackTrace
        )

    }


    /**
     * Lança a exceção armazenada em [stackTrace], se ela existir.
     * Utilizada para propagar erros que ocorreram durante o processamento da requisição.
     * Ao triggar a exception, o stackTrace sai da pilha de execução e volta para o Handler,
     * construindo adequadamente a resposta da API.
     */
    fun throwError() {
        if (stackTrace != null) {
            throw stackTrace
        } else {
            throw IllegalStateException("Nenhum erro para lançar; stackTrace é nulo.")
        }
    }

}
