package dnit.commons.utils

import dnit.commons.exception.CommonException
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RetryUtilsTest {

    // Runtime exception for testing (no checked exception issues)
    class TestException(message : String) : RuntimeException(message)

    // Custom mock implementation for Java 8 compatibility
    class MockCallable<T>(private vararg val responses : Any?) : () -> T {
        private var callCount = 0
        var invocationCount = 0
            private set

        @Suppress("UNCHECKED_CAST")
        override fun invoke() : T {
            invocationCount++
            val response = responses[minOf(callCount++, responses.size - 1)]
            if (response is Throwable) {
                throw response
            }
            return response as T
        }
    }

    // Custom mock implementation for suspending functions
    class MockSuspendableCallable<T>(private vararg val responses : Any?) : suspend () -> T {
        private var callCount = 0
        var invocationCount = 0
            private set

        @Suppress("UNCHECKED_CAST")
        override suspend fun invoke() : T {
            invocationCount++
            val response = responses[minOf(callCount++, responses.size - 1)]
            if (response is Throwable) {
                throw response
            }
            return response as T
        }
    }

    @Nested
    inner class RunWithRetryTest {

        @Test
        fun `should return result on first successful attempt`() {
            // Given
            val expectedResult = "success"
            val callable = MockCallable<String>(expectedResult)

            // When
            val result = runWithRetry(callable)

            // Then
            assertEquals(expectedResult, result)
            assertEquals(1, callable.invocationCount)
        }

        @Test
        fun `should retry and succeed on second attempt`() {
            // Given
            val expectedResult = "success"
            val callable = MockCallable<String>(TestException("fail"), expectedResult)

            // When
            val result = runWithRetry(callable, maxRetries = 3, delayMs = 10)

            // Then
            assertEquals(expectedResult, result)
            assertEquals(2, callable.invocationCount)
        }

        @Test
        fun `should throw original exception when max retries exceeded`() {
            // Given
            val exception = TestException("persistent failure")
            val callable = MockCallable<String>(exception, exception)

            // When & Then
            val thrownException = assertThrows<TestException> {
                runWithRetry(callable, maxRetries = 2, delayMs = 10)
            }

            assertEquals("persistent failure", thrownException.message)
            assertEquals(2, callable.invocationCount)
        }

        @Test
        fun `should return default value when provided and max retries exceeded`() {
            // Given
            val defaultValue = "default"
            val callable = MockCallable<String>(TestException("fail"), TestException("fail"))

            // When
            val result =
                runWithRetry(callable, maxRetries = 2, delayMs = 10, defaultValue = defaultValue)

            // Then
            assertEquals(defaultValue, result)
            assertEquals(2, callable.invocationCount)
        }

        @Test
        fun `should handle custom retry parameters`() {
            // Given
            val expectedResult = "success"
            val callable = MockCallable<String>(TestException("fail"), expectedResult)

            // When
            val startTime = System.currentTimeMillis()
            val result = runWithRetry(callable, maxRetries = 10, delayMs = 50)
            val endTime = System.currentTimeMillis()

            // Then
            assertEquals(expectedResult, result)
            // Verify that delay was applied (should be at least 50ms)
            assertTrue(endTime - startTime >= 50)
            assertEquals(2, callable.invocationCount)
        }

        @Test
        fun `should handle null default value correctly`() {
            // Given
            val callable = MockCallable<String?>(null)

            // When
            val result = runWithRetry(callable, defaultValue = null)

            // Then
            assertNull(result)
            assertEquals(1, callable.invocationCount)
        }
    }

    @Nested
    inner class RunSuspendableWithRetryTest {


        @Test
        fun `should handle nullable return types`() {
            runTest {
                // Given
                val callable = MockSuspendableCallable<String?>(null)

                // When
                val result = runSuspendableWithRetry(callable)

                // Then
                assertNull(result)
                assertEquals(1, callable.invocationCount)
            }
        }

    }

    @Nested
    inner class EdgeCasesTest {

        @Test
        fun `runWithRetry should handle zero max retries`() {
            // Given
            val callable = MockCallable<String>(TestException("fail"))

            // When & Then
            assertThrows<CommonException> {
                runWithRetry(callable, maxRetries = 0, delayMs = 10)
            }
            assertEquals(0, callable.invocationCount)
        }

        @Test
        fun `runSuspendableWithRetry should handle zero max retries`() {
            runTest {
                // Given
                val callable = MockSuspendableCallable<String>(TestException("fail"))

                // When & Then
                assertThrows<CommonException> {
                    runSuspendableWithRetry(callable, maxRetries = 0, delayMs = 10)
                }
                assertEquals(0, callable.invocationCount)
            }
        }

        @Test
        fun `should handle RuntimeException properly in runWithRetry`() {
            // Given
            val callable = MockCallable<String>(
                RuntimeException("runtime error"),
                RuntimeException("runtime error")
            )

            // When & Then
            val exception = assertThrows<RuntimeException> {
                runWithRetry(callable, maxRetries = 2, delayMs = 10)
            }
            assertEquals("runtime error", exception.message)
            assertEquals(2, callable.invocationCount)
        }
    }
}