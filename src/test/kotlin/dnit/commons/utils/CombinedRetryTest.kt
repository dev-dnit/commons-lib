package dnit.commons.utils

import dnit.commons.exception.CommonException
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CombinedRetryTest {

    // Runtime exception for testing
    class TestException(message : String) : RuntimeException(message)

    @Test
    fun `test runWithRetry success`() {
        // Given
        var callCount = 0
        val callable = {
            callCount++
            "success"
        }

        // When
        val result = runWithRetry(callable, maxRetries = 3, delayMs = 10)

        // Then
        assertEquals("success", result)
        assertEquals(1, callCount)
    }

    @Test
    fun `test runWithRetry retry and succeed`() {
        // Given
        var callCount = 0
        val callable = {
            callCount++
            if (callCount < 2) throw TestException("fail")
            "success"
        }

        // When
        val result = runWithRetry(callable, maxRetries = 3, delayMs = 10)

        // Then
        assertEquals("success", result)
        assertEquals(2, callCount)
    }

    @Test
    fun `test runWithRetry max retries exceeded`() {
        // Given
        var callCount = 0
        val callable = {
            callCount++
            throw TestException("fail")
        }

        // When & Then
        val exception = assertThrows<TestException> {
            runWithRetry(callable, maxRetries = 2, delayMs = 10)
        }

        assertEquals("fail", exception.message)
        assertEquals(2, callCount)
    }

    @Test
    fun `test runWithRetry default value`() {
        // Given
        var callCount = 0
        val callable = {
            callCount++
            throw TestException("fail")
        }

        // When
        val result = runWithRetry(callable, maxRetries = 2, delayMs = 10, defaultValue = "default")

        // Then
        assertEquals("default", result)
        assertEquals(2, callCount)
    }

    @Test
    fun `test runSuspendableWithRetry success`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                "success"
            }

            // When
            val result = runSuspendableWithRetry(callable, maxRetries = 3, delayMs = 10)

            // Then
            assertEquals("success", result)
            assertEquals(1, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry retry and succeed`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                if (callCount < 2) throw TestException("fail")
                "success"
            }

            // When
            val result = runSuspendableWithRetry(callable, maxRetries = 3, delayMs = 10)

            // Then
            assertEquals("success", result)
            assertEquals(2, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry retry and succeed on third attempt`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                if (callCount < 3) throw TestException("fail")
                "success"
            }

            // When
            val result = runSuspendableWithRetry(callable, maxRetries = 5, delayMs = 10)

            // Then
            assertEquals("success", result)
            assertEquals(3, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry max retries exceeded`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                throw TestException("fail")
            }

            // When & Then
            val exception = assertThrows<TestException> {
                runSuspendableWithRetry(callable, maxRetries = 2, delayMs = 10)
            }

            assertEquals("fail", exception.message)
            assertEquals(2, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry default value`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                throw TestException("fail")
            }

            // When
            val result = runSuspendableWithRetry(
                callable,
                maxRetries = 2,
                delayMs = 10,
                defaultValue = "default"
            )

            // Then
            assertEquals("default", result)
            assertEquals(2, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry with RuntimeException`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                throw RuntimeException("runtime error")
            }

            // When & Then
            val exception = assertThrows<RuntimeException> {
                runSuspendableWithRetry(callable, maxRetries = 2, delayMs = 10)
            }

            assertEquals("runtime error", exception.message)
            assertEquals(2, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry respect custom delay`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                if (callCount < 2) throw TestException("fail")
                "success"
            }

            // When
            val result = runSuspendableWithRetry(callable, maxRetries = 3, delayMs = 100)

            // Then
            assertEquals("success", result)
            assertEquals(2, callCount)
        }
    }

    @Test
    fun `test runSuspendableWithRetry with zero max retries`() {
        runTest {
            // Given
            var callCount = 0
            val callable = suspend {
                callCount++
                throw TestException("fail")
            }

            // When & Then
            val exception = assertThrows<CommonException> {
                runSuspendableWithRetry(callable, maxRetries = 0, delayMs = 10)
            }

            assertTrue(exception.message!!.contains("Numero maximo de tentativas (0) atingido"))
            assertEquals(0, callCount)
        }
    }
}