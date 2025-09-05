package dnit.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstaqueamentoUtilsTest {

    @Nested
    @DisplayName("nextInterval rounding behavior")
    class NextInterval {

        @Test
        @DisplayName("Rounds up to the next multiple when value is between multiples")
        void roundsUpBetweenMultiples() {
            assertEquals(10.0, EstaqueamentoUtils.nextInterval(1.0, 10.0), 1e-9);
            assertEquals(20.0, EstaqueamentoUtils.nextInterval(10.1, 10.0), 1e-9);
            assertEquals(30.0, EstaqueamentoUtils.nextInterval(21.0, 10.0), 1e-9);
            assertEquals(3.0, EstaqueamentoUtils.nextInterval(2.1, 1.0), 1e-9);
            assertEquals(2.5, EstaqueamentoUtils.nextInterval(2.1, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Rounds up to the next negative multiple when value is between multiples")
        void roundsUpBetweenMultiplesNegativeValues() {
            assertEquals(-10.0, EstaqueamentoUtils.nextInterval(-20.0, 10.0), 1e-9);
            assertEquals(-10.0, EstaqueamentoUtils.nextInterval(-15.0, 10.0), 1e-9);
            assertEquals(0.0, EstaqueamentoUtils.nextInterval(-10.0, 10.0), 1e-9);
            assertEquals(-1.0, EstaqueamentoUtils.nextInterval(-2.0, 1.0), 1e-9);
            assertEquals(-1.5, EstaqueamentoUtils.nextInterval(-2.0, 0.5), 1e-9);
            assertEquals(0.0, EstaqueamentoUtils.nextInterval(-2.0, 10.0), 1e-9);
        }

        @Test
        @DisplayName("When value is exactly on a multiple, returns the next interval (strictly greater)")
        void onExactMultipleReturnsNext() {
            assertEquals(10.0, EstaqueamentoUtils.nextInterval(0.0, 10.0), 1e-9);
            assertEquals(10.0, EstaqueamentoUtils.nextInterval(1.0, 10.0), 1e-9);
            assertEquals(10.0, EstaqueamentoUtils.nextInterval(5.0, 10.0), 1e-9);
            assertEquals(10.0, EstaqueamentoUtils.nextInterval(9.0, 10.0), 1e-9);
            assertEquals(20.0, EstaqueamentoUtils.nextInterval(10.0, 10.0), 1e-9);
            assertEquals(30.0, EstaqueamentoUtils.nextInterval(20.0, 10.0), 1e-9);
            assertEquals(2.0, EstaqueamentoUtils.nextInterval(1.0, 1.0), 1e-9);
            assertEquals(2.5, EstaqueamentoUtils.nextInterval(2.0, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Handles floating point epsilon right below the next multiple by rounding up to it")
        void handlesEpsilonBelowBoundary() {
            // value is just below the boundary due to floating point; should round up to boundary
            assertEquals(20.0, EstaqueamentoUtils.nextInterval(19.99, 10.0), 1e-9);
            assertEquals(2.5, EstaqueamentoUtils.nextInterval(2.49, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Handles floating point epsilon at the next multiple by moving to the following one")
        void handlesEpsilonAtBoundaryAsExact() {
            // if value + epsilon >= nextMultiple, it should go to the next one
            // 19.999999 + 1e-5 = 20.000009 >= 20 -> next is 30
            assertEquals(30.0, EstaqueamentoUtils.nextInterval(19.9999999999, 10.0), 1e-9);
            // 2.49999 + 1e-5 = 2.50000 >= 2.5 -> next is 3.0
            assertEquals(3.0, EstaqueamentoUtils.nextInterval(2.4999999999, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Works with small intervals and decimals")
        void worksWithSmallIntervals() {
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.101, 0.1), 1e-9);
            assertEquals(0.3, EstaqueamentoUtils.nextInterval(0.201, 0.1), 1e-9);
        }

        @Test
        @DisplayName("multiples of 0.1 return the next multiple")
        void exactMultiplesReturnNext() {
            assertEquals(0.1, EstaqueamentoUtils.nextInterval(0.0, 0.1), 1e-9);
            assertEquals(0.1, EstaqueamentoUtils.nextInterval(0.01, 0.1), 1e-9);
            assertEquals(0.1, EstaqueamentoUtils.nextInterval(0.05, 0.1), 1e-9);
            assertEquals(0.1, EstaqueamentoUtils.nextInterval(0.09, 0.1), 1e-9);
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.1, 0.1), 1e-9);
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.12, 0.1), 1e-9);
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.15, 0.1), 1e-9);
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.19, 0.1), 1e-9);
            assertEquals(1.3, EstaqueamentoUtils.nextInterval(1.2, 0.1), 1e-9);
        }

        @Test
        @DisplayName("Values between 0.1 multiples round up to the next 0.1")
        void betweenMultiplesRoundUp() {
            assertEquals(0.1, EstaqueamentoUtils.nextInterval(0.01, 0.1), 1e-9);
            assertEquals(0.2, EstaqueamentoUtils.nextInterval(0.11, 0.1), 1e-9);
            assertEquals(1.3, EstaqueamentoUtils.nextInterval(1.21, 0.1), 1e-9);
        }
    }



    @Nested
    @DisplayName("currentInterval rounding behavior")
    class CurrentInterval {

        @Test
        @DisplayName("Returns start of interval for values between multiples (positive values)")
        void roundsDownToStartOfIntervalForPositiveValues() {
            // interval 10: [0,10) -> 0 ; [10,20) -> 10 ; etc.
            assertEquals(0.0, EstaqueamentoUtils.currentInterval(0.1, 10.0), 1e-9);
            assertEquals(0.0, EstaqueamentoUtils.currentInterval(9.99, 10.0), 1e-9);
            assertEquals(10.0, EstaqueamentoUtils.currentInterval(10.1, 10.0), 1e-9);
            assertEquals(20.0, EstaqueamentoUtils.currentInterval(21.0, 10.0), 1e-9);

            // interval 0.5
            assertEquals(2.0, EstaqueamentoUtils.currentInterval(2.1, 0.5), 1e-9);
            assertEquals(2.5, EstaqueamentoUtils.currentInterval(2.6, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Values exactly on a multiple return that multiple as current")
        void exactMultipleReturnsSameAsCurrent() {
            assertEquals(0.0, EstaqueamentoUtils.currentInterval(0.0, 10.0), 1e-9);
            assertEquals(10.0, EstaqueamentoUtils.currentInterval(10.0, 10.0), 1e-9);
            assertEquals(20.0, EstaqueamentoUtils.currentInterval(20.0, 10.0), 1e-9);

            assertEquals(1.0, EstaqueamentoUtils.currentInterval(1.0, 1.0), 1e-9);
            assertEquals(2.0, EstaqueamentoUtils.currentInterval(2.0, 0.5), 1e-9);
            assertEquals(2.5, EstaqueamentoUtils.currentInterval(2.5, 0.5), 1e-9);
        }

        @Test
        @DisplayName("Handles epsilon around the boundary consistently with nextKm logic")
        void handlesEpsilonAroundBoundary() {
            // Just below boundary -> current should be start of current interval
            assertEquals(10.0, EstaqueamentoUtils.currentInterval(19.99, 10.0), 1e-9); // next=20 -> current=10
            assertEquals(2.0, EstaqueamentoUtils.currentInterval(2.49, 0.5), 1e-9);    // next=2.5 -> current=2.0

            // At boundary within epsilon, nextKm jumps to following interval, so current moves to boundary
            assertEquals(20.0, EstaqueamentoUtils.currentInterval(19.9999999999, 10.0), 1e-9); // next=30 -> current=20
            assertEquals(2.5, EstaqueamentoUtils.currentInterval(2.4999999999, 0.5), 1e-9);     // next=3.0 -> current=2.5
        }

        @Test
        @DisplayName("Works with small intervals and decimals for currentKm")
        void worksWithSmallIntervals() {
            assertEquals(0.1, EstaqueamentoUtils.currentInterval(0.101, 0.1), 1e-9); // next=0.2 -> current=0.1
            assertEquals(0.2, EstaqueamentoUtils.currentInterval(0.201, 0.1), 1e-9); // next=0.3 -> current=0.2
            assertEquals(1.2, EstaqueamentoUtils.currentInterval(1.21, 0.1), 1e-9);  // next=1.3 -> current=1.2
        }

        @Test
        @DisplayName("Negative values align to the start of their interval as well")
        void negativeValues() {
            // For negative numbers, nextKm moves towards zero/upwards, so current moves one interval below that
            assertEquals(-20.0, EstaqueamentoUtils.currentInterval(-11.0, 10.0), 1e-9); // next=-10 -> current=-20
            assertEquals(-20.0, EstaqueamentoUtils.currentInterval(-10.1, 10.0), 1e-9); // next=0 -> current=-10
            assertEquals(-2.0, EstaqueamentoUtils.currentInterval(-1.1, 1.0), 1e-9);    // next=-1 -> current=-2
            assertEquals(-2.0, EstaqueamentoUtils.currentInterval(-2.0, 1.0), 1e-9);    // exact multiple -> same
            assertEquals(-2.5, EstaqueamentoUtils.currentInterval(-2.4, 0.5), 1e-9);    // next=-2.0 -> current=-2.5
        }

        @Test
        @DisplayName("Zero behaves as start of the first interval")
        void zeroBehavior() {
            assertEquals(0.0, EstaqueamentoUtils.currentInterval(0.0, 10.0), 1e-9);
            assertEquals(0.0, EstaqueamentoUtils.currentInterval(0.0, 0.1), 1e-9);
        }
    }
}
