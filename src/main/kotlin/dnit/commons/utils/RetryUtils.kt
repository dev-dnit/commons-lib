package dnit.commons.utils

import dnit.commons.exception.CommonException
import kotlinx.coroutines.delay


/**
 * Executa uma função com lógica de retry
 * @param callable: Função a ser executada
 * @param maxRetries: Número máximo de re-tentativas
 * @return T? O resultado da função executada
 */
fun <T> runWithRetry(
    callable : () -> T,
    maxRetries : Int = 5,
    delayMs: Long = 2_000L,
    defaultValue : T? = null,
) : T {

    var attempt = 0

    while (attempt < maxRetries) {
        try {
            return callable()

        } catch (e : Exception) {
            attempt++
            if (attempt >= maxRetries) {
                // If we've reached max retries and have a default value, return it
                if (defaultValue != null) return defaultValue
                // Otherwise throw the exception
                throw e
            }

            Thread.sleep(delayMs)
        }
    }

    if (defaultValue != null) return defaultValue
    throw CommonException("Numero maximo de tentativas ($maxRetries) atingido. Desistindo.")
}



/**
 * Executa uma função com lógica de retry
 * @param callable: Função a ser executada
 * @param maxRetries: Número máximo de re-tentativas
 * @return T? O resultado da função executada
 */
suspend fun <T> runSuspendableWithRetry(
    callable : suspend () -> T,
    maxRetries : Int = 5,
    delayMs: Long = 2_000L,
    defaultValue : T? = null,
) : T {

    var attempt = 0

    while (attempt < maxRetries) {
        try {
            return callable()

        } catch (e : Exception) {
            attempt++
            if (attempt >= maxRetries) {
                // If we've reached max retries and have a default value, return it
                if (defaultValue != null) return defaultValue
                // Otherwise throw the exception
                throw e
            }

            delay(delayMs)
        }
    }

    if (defaultValue != null) return defaultValue
    throw CommonException("Numero maximo de tentativas ($maxRetries) atingido. Desistindo.")
}
