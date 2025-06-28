package dnit.commons.utils

import dnit.commons.exception.CommonException


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
                throw e
            }

            Thread.sleep(delayMs)
        }
    }

    if (defaultValue != null) return defaultValue
    throw CommonException("Numero maximo de tentativas ($maxRetries) atingido. Desistindo.")
}

