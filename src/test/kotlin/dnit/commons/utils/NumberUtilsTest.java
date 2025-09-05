package dnit.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {

    private static final double EPS = 1e-9;

    @Nested
    @DisplayName("calculoMedia")
    class CalculoMedia {

        @Test
        @DisplayName("deve calcular média de valores típicos")
        void shouldCalculateAverageForTypicalValues() {
            Double result = NumberUtils.calculoMedia(2.0, 4.0, 6.0);
            assertNotNull(result);
            assertEquals(4.0, result, EPS);
        }

        @Test
        @DisplayName("deve retornar null quando array for null")
        void shouldReturnNullWhenArrayIsNull() {
            assertNull(NumberUtils.calculoMedia((Double[]) null));
        }

        @Test
        @DisplayName("deve retornar null quando array for vazio")
        void shouldReturnNullWhenArrayIsEmpty() {
            assertNull(NumberUtils.calculoMedia());
        }

        @Test
        @DisplayName("deve ignorar valores null no cálculo")
        void shouldIgnoreNullValues() {
            Double result = NumberUtils.calculoMedia(1.0, null, 3.0);
            assertNotNull(result);
            assertEquals(2.0, result, EPS);
        }

        @Test
        @DisplayName("deve lançar NoSuchElementException quando todos forem null")
        void shouldThrowWhenAllValuesAreNull() {
            assertThrows(NoSuchElementException.class, () -> NumberUtils.calculoMedia(null, null));
        }

        @Test
        @DisplayName("deve calcular média com números negativos")
        void shouldHandleNegativeNumbers() {
            Double result = NumberUtils.calculoMedia(-2.0, -4.0, -6.0);
            assertNotNull(result);
            assertEquals(-4.0, result, EPS);
        }

        @Test
        @DisplayName("deve calcular média com mistura de negativos e positivos")
        void shouldHandleMixedPositiveAndNegative() {
            Double result = NumberUtils.calculoMedia(-2.0, 2.0, 4.0, -4.0);
            assertNotNull(result);
            assertEquals(0.0, result, EPS);
        }

        @Test
        @DisplayName("deve calcular média com grandes quantidades de números e valores null intercalados")
        void shouldHandleManyNumbersWithNulls() {
            Double result = NumberUtils.calculoMedia(1.0, null, 2.0, 3.0, null, 4.0, 5.0, null);
            assertNotNull(result);
            assertEquals(3.0, result, EPS);
        }

        @Test
        @DisplayName("deve calcular média com double infinito e NaN ignorando apenas nulls (Infinity e NaN contam e propagam)")
        void shouldPropagateInfinityAndNaN() {
            // O stream não filtra NaN/Infinity, apenas nulls. A média de (1.0, NaN) resulta em NaN
            assertTrue(Double.isNaN(NumberUtils.calculoMedia(1.0, Double.NaN)));
            assertEquals(Double.POSITIVE_INFINITY, NumberUtils.calculoMedia(1.0, Double.POSITIVE_INFINITY));
        }

    }


    @Nested
    @DisplayName("calculoVariancia (populacional)")
    class CalculoVariancia {

        @Test
        @DisplayName("deve calcular variância exemplo - 1")
        void shouldCalculateVarianciaWithExampleFromMundoEducacaoExample1() {
            Double result = NumberUtils.calculoVariancia(10.0, 9.0, 11.0, 12.0, 8.0);
            assertNotNull(result);
            assertEquals(2.0, result, EPS);
        }

        @Test
        @DisplayName("deve calcular variância exemplo - 2")
        void shouldCalculateVarianciaWithExampleFromMundoEducacaoExample2() {
            Double result = NumberUtils.calculoVariancia(15.0, 12.0, 16.0, 10.0, 11.0);
            assertNotNull(result);
            assertEquals(5.36, result, EPS);
        }

        @Test
        @DisplayName("deve calcular variância exemplo - 3")
        void shouldCalculateVarianciaWithExampleFromMundoEducacaoExample3() {
            Double result = NumberUtils.calculoVariancia(11.0, 10.0, 8.0, 11.0, 12.0);
            assertNotNull(result);
            assertEquals(1.84, result, EPS);
        }

        @Test
        @DisplayName("deve calcular variância exemplo - 4")
        void shouldCalculateVarianciaWithExampleFromMundoEducacaoExample4() {
            Double result = NumberUtils.calculoVariancia(8.0, 12.0, 15.0, 9.0, 11.0);
            assertNotNull(result);
            assertEquals(6.0, result, EPS);
        }

        @Test
        @DisplayName("deve calcular variância populacional de valores típicos")
        void shouldCalculatePopulationVarianceForTypicalValues() {
            // valores: 1, 2, 3 -> média 2 -> variâncias: 1,0,1 -> média = 2/3
            Double result = NumberUtils.calculoVariancia(1.0, 2.0, 3.0);
            assertNotNull(result);
            assertEquals(2.0 / 3.0, result, EPS);
        }

        @Test
        @DisplayName("deve retornar 0 para único valor")
        void shouldReturnZeroForSingleValue() {
            Double result = NumberUtils.calculoVariancia(5.0);
            assertNotNull(result);
            assertEquals(0.0, result, EPS);
        }

        @Test
        @DisplayName("deve retornar null quando array for null")
        void shouldReturnNullWhenArrayIsNull() {
            assertNull(NumberUtils.calculoVariancia((Double[]) null));
        }

        @Test
        @DisplayName("deve retornar null quando array for vazio")
        void shouldReturnNullWhenArrayIsEmpty() {
            assertNull(NumberUtils.calculoVariancia());
        }

        @Test
        @DisplayName("deve ignorar valores null no cálculo")
        void shouldIgnoreNullValues() {
            // Considera apenas 2 e 4 -> média 3 -> var = ((1)^2 + (1)^2)/2 = 1
            Double result = NumberUtils.calculoVariancia(null, 2.0, null, 4.0);
            assertNotNull(result);
            assertEquals(1.0, result, EPS);
        }

        @Test
        @DisplayName("deve retornar null quando todos os valores forem null")
        void shouldReturnNullWhenAllValuesAreNull() {
            assertNull(NumberUtils.calculoVariancia(null, null));
        }
    }


    @Nested
    @DisplayName("calculoDesvioPadrao (populacional)")
    class CalculoDesvioPadrao {

        @Test
        @DisplayName("deve calcular desvio padrao exemplo - 1")
        void shouldCalculateDesvioPadraoWithExampleFromMundoEducacaoExample1() {
            Double result = NumberUtils.calculoDesvioPadrao(10.0, 9.0, 11.0, 12.0, 8.0);
            assertNotNull(result);
            assertEquals(1.4142135623730951, result, EPS);
        }

        @Test
        @DisplayName("deve calcular desvio padrao exemplo - 2")
        void shouldCalculateDesvioPadraoWithExampleFromMundoEducacaoExample2() {
            Double result = NumberUtils.calculoDesvioPadrao(15.0, 12.0, 16.0, 10.0, 11.0);
            assertNotNull(result);
            assertEquals(2.3151673805580453, result, EPS);
        }

        @Test
        @DisplayName("deve calcular desvio padrao exemplo - 3")
        void shouldCalculateDesvioPadraoWithExampleFromMundoEducacaoExample3() {
            Double result = NumberUtils.calculoDesvioPadrao(11.0, 10.0, 8.0, 11.0, 12.0);
            assertNotNull(result);
            assertEquals(1.3564659966250536, result, EPS);
        }

        @Test
        @DisplayName("deve calcular desvio padrao exemplo - 4")
        void shouldCalculateDesvioPadraoWithExampleFromMundoEducacaoExample4() {
            Double result = NumberUtils.calculoDesvioPadrao(8.0, 12.0, 15.0, 9.0, 11.0);
            assertNotNull(result);
            assertEquals(2.449489742783178, result, EPS);
        }

        @Test
        @DisplayName("deve calcular desvio padrão para valores típicos")
        void shouldCalculateStdDevForTypicalValues() {
            // valores: 1,2,3 -> variância populacional = 2/3 -> desvio = sqrt(2/3)
            Double result = NumberUtils.calculoDesvioPadrao(1.0, 2.0, 3.0);
            assertNotNull(result);
            assertEquals(Math.sqrt(2.0 / 3.0), result, EPS);
        }

        @Test
        @DisplayName("deve retornar 0 para único valor")
        void shouldReturnZeroForSingleValue() {
            Double result = NumberUtils.calculoDesvioPadrao(5.0);
            assertNotNull(result);
            assertEquals(0.0, result, EPS);
        }

        @Test
        @DisplayName("deve lidar com números negativos e mistos")
        void shouldHandleNegativeAndMixedNumbers() {
            // valores: -2, 2, 4, -4 -> média 0 -> quadrados: 4,4,16,16 -> média = 10 -> sqrt(10)
            Double result = NumberUtils.calculoDesvioPadrao(-2.0, 2.0, 4.0, -4.0);
            assertNotNull(result);
            assertEquals(Math.sqrt(10.0), result, EPS);
        }

        @Test
        @DisplayName("deve retornar null quando array for null")
        void shouldReturnNullWhenArrayIsNull() {
            assertNull(NumberUtils.calculoDesvioPadrao((Double[]) null));
        }

        @Test
        @DisplayName("deve retornar null quando array for vazio")
        void shouldReturnNullWhenArrayIsEmpty() {
            assertNull(NumberUtils.calculoDesvioPadrao());
        }

        @Test
        @DisplayName("deve ignorar valores null no cálculo (usa apenas não-nulos)")
        void shouldIgnoreNullValues() {
            // Considera apenas 2 e 4 -> variância = 1 -> desvio = 1
            Double result = NumberUtils.calculoDesvioPadrao(null, 2.0, null, 4.0);
            assertNotNull(result);
            assertEquals(1.0, result, EPS);
        }
    }
}
