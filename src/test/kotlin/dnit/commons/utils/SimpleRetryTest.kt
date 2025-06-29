package dnit.commons.utils

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SimpleRetryTest {

    // Runtime exception for testing
    class TestException(message: String) : RuntimeException(message)

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
            val result = runSuspendableWithRetry(callable, maxRetries = 2, delayMs = 10, defaultValue = "default")

            // Then
            assertEquals("default", result)
            assertEquals(2, callCount)
        }
    }
}