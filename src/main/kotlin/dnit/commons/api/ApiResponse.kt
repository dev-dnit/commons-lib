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
data class ApiResponse(

    val result : Any? = null,
    val info : InfoResult? = null,
    val pagination : PageMetadata? = null,
    val stackTrace : Throwable? = null,
    val validation : Any? = null

) {

    companion object {

        @JvmStatic
        fun success(
            result : Any? = null
        ) = ApiResponse(
            result = result,
            info = InfoResult(InfoType.SUCCESS, null)
        )


        @JvmStatic
        fun success(
            result : Any? = null,
            message : String? = null
        ) = ApiResponse(
            result = result,
            info = InfoResult(InfoType.SUCCESS, message)
        )


        @JvmStatic
        fun success(
            result : Any? = null,
            pagination : PageMetadata? = null
        ) = ApiResponse(
            result = result,
            pagination = pagination,
            info = InfoResult(InfoType.SUCCESS, null)
        )


        @JvmStatic
        fun success(
            result : Any? = null,
            message : String? = null,
            pagination : PageMetadata? = null,
        ) = ApiResponse(
            result = result,
            pagination = pagination,
            info = InfoResult(InfoType.SUCCESS, message),
        )


        @JvmStatic
        @JvmOverloads
        fun info(
            result : Any? = null,
            message : String? = null,
            pagination : PageMetadata? = null,
        ) = ApiResponse(
            result = result,
            info = InfoResult(InfoType.INFO, message),
            pagination = pagination
        )


        @JvmStatic
        fun error(
            message : String? = null
        ) = ApiResponse(
            result = null,
            info = InfoResult(InfoType.ERROR, message)
        )


        @JvmStatic
        fun validationError(
            message: String? = null,
            validations: Any? = null,
        ): ApiResponse{
            return ApiResponse(
                result = null,
                info = InfoResult(InfoType.ERROR, message),
                validation = validations
            )
        }


        @JvmStatic
        fun error(
            message : String? = null,
            stackTrace : Throwable? = null,
        ) = ApiResponse(
            result = null,
            info = InfoResult(InfoType.ERROR, message),
            stackTrace = stackTrace
        )


        @JvmStatic
        fun warn(
            message : String? = null
        ) = ApiResponse(
            result = null,
            info = InfoResult(InfoType.WARNING, message)
        )


        @JvmStatic
        fun warn(
            message : String? = null,
            stackTrace : Throwable? = null,
        ) = ApiResponse(
            result = null,
            info = InfoResult(InfoType.WARNING, message),
            stackTrace = stackTrace
        )

    }

}
