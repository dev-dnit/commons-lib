package dnit.commons.api

class ApiResult<T> {
    var result: T? = null
    var info: InfoResult? = null
    var stackTrace: String? = null
    var pagination: PageMetadata? = null

    companion object {

        @JvmStatic
        fun <T> success(result: T): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;

            newResult.info = InfoResult()
            newResult.info!!.type = InfoType.SUCCESS

            return newResult
        }

        @JvmStatic
        fun <T> success(result: T, message: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;

            newResult.info = InfoResult()
            newResult.info!!.message = message
            newResult.info!!.type = InfoType.SUCCESS

            return newResult
        }

        @JvmStatic
        fun <T> success(result: T, pagination: PageMetadata? = null, message: String? = null): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;
            newResult.pagination = pagination;

            newResult.info = InfoResult()
            newResult.info!!.message = message
            newResult.info!!.type = InfoType.SUCCESS

            return newResult
        }

        @JvmStatic
        fun <T> success(result: T, pagination: PageMetadata? = null): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;
            newResult.pagination = pagination;

            newResult.info = InfoResult()
            newResult.info!!.type = InfoType.SUCCESS

            return newResult
        }

        @JvmStatic
        fun <T> info(result: T, message: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;
            newResult.info = InfoResult()
            newResult.info!!.message = message
            newResult.info!!.type = InfoType.INFO

            return newResult
        }

        @JvmStatic
        fun <T> info(result: T, message: String, stackTrace: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = result;
            newResult.stackTrace = stackTrace;
            newResult.info = InfoResult()
            newResult.info!!.message = message
            newResult.info!!.type = InfoType.INFO

            return newResult
        }

        @JvmStatic
        fun <T> error(message: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = null;
            newResult.info = InfoResult();
            newResult.info!!.message = message;
            newResult.info!!.type = InfoType.ERROR;
            return newResult
        }

        @JvmStatic
        fun <T> error(message: String, stackTrace: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = null;
            newResult.info = InfoResult();
            newResult.stackTrace = stackTrace;
            newResult.info!!.message = message;
            newResult.info!!.type = InfoType.ERROR;
            return newResult
        }

        @JvmStatic
        fun <T> warning(message: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = null;
            newResult.info = InfoResult();
            newResult.info!!.message = message;
            newResult.info!!.type = InfoType.WARNING;
            return newResult
        }

        @JvmStatic
        fun <T> warning(message: String, stackTrace: String): ApiResult<T> {
            val newResult = ApiResult<T>();
            newResult.result = null;
            newResult.info = InfoResult();
            newResult.stackTrace = stackTrace;
            newResult.info!!.message = message;
            newResult.info!!.type = InfoType.WARNING;
            return newResult
        }
    }
}

enum class InfoType {
    SUCCESS,
    INFO,
    ERROR,
    WARNING,
    NORMAL
}

class InfoResult {
    var type: InfoType = InfoType.NORMAL;
    var message: String? = null
}

class PageMetadata {
    var count: Int = 0;
    var total: Int = 0;
    var pages: Int = 0;
    var currentPage: Int = 0;

    val hasNextPage: Boolean
        get() = currentPage < total

    val hasPreviousPage: Boolean
        get() = currentPage > 0
}