package dnit.commons.infrastructure.session

import dnit.commons.exception.CommonException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ContextSession Tests")
class ContextSessionTest {

    @Nested
    @DisplayName("Constructor Tests")
    inner class ConstructorTests {

        @Test
        @DisplayName("Should create ContextSession with generated sessionId")
        fun shouldCreateContextSessionWithGeneratedSessionId() {
            // When
            val session = ContextSession()

            // Then
            assertNotNull(session.sessionId, "SessionId should not be null")
            assertTrue(session.sessionId.isNotEmpty(), "SessionId should not be empty")
            assertThrows(NullPointerException::class.java) { session.startTime }
            assertThrows(NullPointerException::class.java) { session.endTime }
        }

        @Test
        @DisplayName("Should create ContextSession with provided sessionId")
        fun shouldCreateContextSessionWithProvidedSessionId() {
            // Given
            val providedSessionId = "customSessionId"

            // When
            val session = ContextSession(providedSessionId)

            // Then
            assertEquals(
                providedSessionId,
                session.sessionId,
                "SessionId should match the provided ID"
            )
            assertThrows(NullPointerException::class.java) { session.startTime }
            assertThrows(NullPointerException::class.java) { session.endTime }
        }
    }

    @Nested
    @DisplayName("MDC Registration Tests")
    inner class MDCRegistrationTests {

        @Test
        @DisplayName("Should return same instance after registerInMDC")
        fun shouldReturnSameInstanceAfterRegisterInMDC() {
            // Given
            val sessionId = "testSessionId"
            val session = ContextSession(sessionId)

            // When
            val result = session.registerInMDC()

            // Then
            assertSame(session, result, "Method should return this instance for method chaining")
        }
    }

    @Nested
    @DisplayName("Session Timing Tests")
    inner class SessionTimingTests {

        private lateinit var session : ContextSession
        private val fixedSessionId = "testSessionId"

        @BeforeEach
        fun setup() {
            session = ContextSession(fixedSessionId)
        }

        @Test
        @DisplayName("Should set start time when startSessionTime is called")
        fun shouldSetStartTimeWhenStartSessionTimeCalled() {
            // When
            val result = session.startSessionTime()

            // Then
            assertDoesNotThrow { session.startTime }
            assertSame(session, result, "Method should return this instance for method chaining")
        }

        @Test
        @DisplayName("Should set end time when finishSessionTime is called")
        fun shouldSetEndTimeWhenFinishSessionTimeCalled() {
            // When
            val result = session.finishSessionTime()

            // Then
            assertDoesNotThrow { session.endTime }
            assertSame(session, result, "Method should return this instance for method chaining")
        }

        @Test
        @DisplayName("Should calculate session duration correctly")
        fun shouldCalculateSessionDurationCorrectly() {
            // Given
            session.startSessionTime()
            // Wait a small amount of time to ensure duration is measurable
            Thread.sleep(10)
            session.finishSessionTime()

            // When
            val duration = session.sessionDurationTime()

            // Then
            assertNotNull(duration, "Duration should not be null")
            assertTrue(duration.isNotEmpty(), "Duration should not be empty")
            assertTrue(duration.contains("ms"), "Duration should contain milliseconds")
        }
    }

    @Nested
    @DisplayName("Getter Method Tests")
    inner class GetterMethodTests {

        private lateinit var session : ContextSession
        private val fixedSessionId = "testSessionId"

        @BeforeEach
        fun setup() {
            session = ContextSession(fixedSessionId)
            session.startSessionTime()
            session.finishSessionTime()
        }

        @Test
        @DisplayName("getStartTime should return string representation of start time")
        fun getStartTimeShouldReturnStringRepresentationOfStartTime() {
            // When
            val result = session.startTime

            // Then
            assertNotNull(result, "Start time should not be null")
            assertTrue(result.isNotEmpty(), "Start time should not be empty")
        }

        @Test
        @DisplayName("getEndTime should return string representation of end time")
        fun getEndTimeShouldReturnStringRepresentationOfEndTime() {
            // When
            val result = session.endTime

            // Then
            assertNotNull(result, "End time should not be null")
            assertTrue(result.isNotEmpty(), "End time should not be empty")
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    inner class EdgeCaseTests {

        @Test
        @DisplayName("sessionDurationTime should throw CommonException when start time is null")
        fun sessionDurationTimeShouldThrowCommonExceptionWhenStartTimeIsNull() {
            // Given
            val session = ContextSession("testId")
            session.finishSessionTime() // Only set end time

            // When & Then
            val exception = assertThrows(CommonException::class.java) {
                session.sessionDurationTime()
            }
            assertEquals("Os instantes não podem ser nulos", exception.message)
        }

        @Test
        @DisplayName("sessionDurationTime should throw CommonException when end time is null")
        fun sessionDurationTimeShouldThrowCommonExceptionWhenEndTimeIsNull() {
            // Given
            val session = ContextSession("testId")
            session.startSessionTime() // Only set start time

            // When & Then
            val exception = assertThrows(CommonException::class.java) {
                session.sessionDurationTime()
            }
            assertEquals("Os instantes não podem ser nulos", exception.message)
        }

        @Test
        @DisplayName("getStartTime should throw NullPointerException when start time is null")
        fun getStartTimeShouldThrowNullPointerExceptionWhenStartTimeIsNull() {
            // Given
            val session = ContextSession("testId")

            // When & Then
            assertThrows(NullPointerException::class.java) {
                session.startTime
            }
        }

        @Test
        @DisplayName("getEndTime should throw NullPointerException when end time is null")
        fun getEndTimeShouldThrowNullPointerExceptionWhenEndTimeIsNull() {
            // Given
            val session = ContextSession("testId")

            // When & Then
            assertThrows(NullPointerException::class.java) {
                session.endTime
            }
        }
    }

    @Nested
    @DisplayName("Complete Workflow Tests")
    inner class CompleteWorkflowTests {

        @Test
        @DisplayName("Should handle complete session lifecycle")
        fun shouldHandleCompleteSessionLifecycle() {
            // Given
            val session = ContextSession("workflowTestId")

            // When - Start the session
            val startedSession = session.startSessionTime()

            // Then - Verify start time is set
            assertNotNull(session.startTime)
            assertSame(
                session,
                startedSession,
                "Method should return this instance for method chaining"
            )

            // When - Finish the session
            val finishedSession = session.finishSessionTime()

            // Then - Verify end time is set
            assertNotNull(session.endTime)
            assertSame(
                session,
                finishedSession,
                "Method should return this instance for method chaining"
            )

            // When - Get duration
            val duration = session.sessionDurationTime()

            // Then - Verify duration is calculated
            assertNotNull(duration)
            assertTrue(duration.isNotEmpty())
        }

        @Test
        @DisplayName("Should support method chaining for complete workflow")
        fun shouldSupportMethodChainingForCompleteWorkflow() {
            // Given
            val session = ContextSession("chainTestId")

            // When - Chain all methods
            val result = session
                .registerInMDC()
                .startSessionTime()
                .finishSessionTime()

            // Then - Verify the result is the same instance
            assertSame(session, result, "Method chaining should return the same instance")

            // And - Verify all operations were performed
            assertNotNull(session.startTime)
            assertNotNull(session.endTime)
            assertNotNull(session.sessionDurationTime())
        }
    }
}
