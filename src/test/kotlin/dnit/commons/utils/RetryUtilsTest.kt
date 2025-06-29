package dnit.commons.utils

import dnit.commons.exception.CommonException
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class RetryUtilsTest {

    // Runtime exception for testing (no checked exception issues)
    class TestException(message : String) : RuntimeException(message)

    @Nested
    inner class RunWithRetryTest {

        @Test
        fun `should return result on first successful attempt`() {
            // Given
            val expectedResult = "success"
            val callable = mock<() -> String> {
                on { invoke() } doReturn expectedResult
            }

            // When
            val result = runWithRetry(callable)

            // Then
            assertEquals(expectedResult, result)
            verify(callable, times(1)).invoke()
        }

        @Test
        fun `should retry and succeed on second attempt`() {
            // Given
            val expectedResult = "success"
            val callable = mock<() -> String> {
                on { invoke() } doThrow TestException("fail") doReturn expectedResult
            }

            // When
            val result = runWithRetry(callable, maxRetries = 3, delayMs = 10)

            // Then
            assertEquals(expectedResult, result)
            verify(callable, times(2)).invoke()
        }

        @Test
        fun `should throw original exception when max retries exceeded`() {
            // Given
            val exception = TestException("persistent failure")
            val callable = mock<() -> String> {
                on { invoke() } doThrow exception
            }

            // When & Then
            val thrownException = assertThrows<TestException> {
                runWithRetry(callable, maxRetries = 2, delayMs = 10)
            }

            assertEquals("persistent failure", thrownException.message)
            verify(callable, times(2)).invoke()
        }

        @Test
        fun `should return default value when provided and max retries exceeded`() {
            // Given
            val defaultValue = "default"
            val callable = mock<() -> String> {
                on { invoke() } doThrow TestException("fail")
            }

            // When
            val result =
                runWithRetry(callable, maxRetries = 2, delayMs = 10, defaultValue = defaultValue)

            // Then
            assertEquals(defaultValue, result)
            verify(callable, times(2)).invoke()
        }

        @Test
        fun `should handle custom retry parameters`() {
            // Given
            val expectedResult = "success"
            val callable = mock<() -> String> {
                on { invoke() } doThrow TestException("fail") doReturn expectedResult
            }

            // When
            val startTime = System.currentTimeMillis()
            val result = runWithRetry(callable, maxRetries = 10, delayMs = 50)
            val endTime = System.currentTimeMillis()

            // Then
            assertEquals(expectedResult, result)
            // Verify that delay was applied (should be at least 50ms)
            assertTrue(endTime - startTime >= 50)
        }

        @Test
        fun `should handle null default value correctly`() {
            // Given
            val callable = mock<() -> String?> {
                on { invoke() } doReturn null
            }

            // When
            val result = runWithRetry(callable, defaultValue = null)

            // Then
            assertNull(result)
        }
    }

    @Nested
    inner class RunSuspendableWithRetryTest {


        @Test
        fun `should handle nullable return types`() {
            runTest {
                // Given
                val callable = mock<suspend () -> String?> {
                    onBlocking { invoke() } doReturn null
                }

                // When
                val result = runSuspendableWithRetry(callable)

                // Then
                assertNull(result)
            }
        }

    }

    @Nested
    inner class EdgeCasesTest {

        @Test
        fun `runWithRetry should handle zero max retries`() {
            // Given
            val callable = mock<() -> String> {
                on { invoke() } doThrow TestException("fail")
            }

            // When & Then
            assertThrows<CommonException> {
                runWithRetry(callable, maxRetries = 0, delayMs = 10)
            }
            verify(callable, never()).invoke()
        }

        @Test
        fun `runSuspendableWithRetry should handle zero max retries`() {
            runTest {
                // Given
                val callable = mock<suspend () -> String> {
                    onBlocking { invoke() } doThrow TestException("fail")
                }

                // When & Then
                assertThrows<CommonException> {
                    runSuspendableWithRetry(callable, maxRetries = 0, delayMs = 10)
                }
                verify(callable, never()).invoke()
            }
        }

        @Test
        fun `should handle RuntimeException properly in runWithRetry`() {
            // Given
            val callable = mock<() -> String> {
                on { invoke() } doThrow RuntimeException("runtime error")
            }

            // When & Then
            val exception = assertThrows<RuntimeException> {
                runWithRetry(callable, maxRetries = 2, delayMs = 10)
            }
            assertEquals("runtime error", exception.message)
        }
    }
}