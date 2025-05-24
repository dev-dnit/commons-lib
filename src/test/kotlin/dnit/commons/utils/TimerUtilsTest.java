package dnit.commons.utils;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TimerUtilsTest {

    @Test
    void testDuracaoSegundosEMilissegundos() {
        Instant start = Instant.parse("2023-01-01T00:00:00.000Z");
        Instant end = Instant.parse("2023-01-01T00:00:05.123Z");

        String resultado = TimerUtils.duracaoEntreDoisInstants(start, end);

        assertEquals("5 segundos 123 ms", resultado);
    }

    @Test
    void testDuracaoComMinutosHorasDias() {
        Instant start = Instant.parse("2023-01-01T00:00:00.000Z");
        Instant end = Instant.parse("2023-01-03T02:15:45.250Z");

        String resultado = TimerUtils.duracaoEntreDoisInstants(start, end);

        assertEquals("2 dias 2 horas 15 minutos 45 segundos 250 ms", resultado);
    }

    @Test
    void testDuracaoZero() {
        Instant now = Instant.now();
        String resultado = TimerUtils.duracaoEntreDoisInstants(now, now);

        assertEquals("0 ms", resultado);
    }

    @Test
    void testDuracaoNegativa() {
        Instant start = Instant.parse("2023-01-01T01:00:00.000Z");
        Instant end = Instant.parse("2023-01-01T00:00:00.000Z");

        String resultado = TimerUtils.duracaoEntreDoisInstants(start, end);

        assertEquals("-1 horas 0 ms", resultado); // Depende do comportamento esperado para valores negativos
    }

    @Test
    void testDuracaoAteNow() {
        Instant start = Instant.now().minusSeconds(2);
        String resultado = TimerUtils.duracaoAteNow(start);

        assertTrue(resultado.contains(" segundos") || resultado.contains("1 segundos"));
    }

    @Test
    void testStartTimeNullLancaExcecao() {
        Instant end = Instant.now();

        CommonException exception = assertThrows(CommonException.class, () ->
                TimerUtils.duracaoEntreDoisInstants(null, end));

        assertEquals("Os instantes não podem ser nulos", exception.getMessage());
    }

    @Test
    void testEndTimeNullLancaExcecao() {
        Instant start = Instant.now();

        CommonException exception = assertThrows(CommonException.class, () ->
                TimerUtils.duracaoEntreDoisInstants(start, null));

        assertEquals("Os instantes não podem ser nulos", exception.getMessage());
    }

    @Test
    void testAmbosNullLancaExcecao() {
        CommonException exception = assertThrows(CommonException.class, () ->
                TimerUtils.duracaoEntreDoisInstants(null, null));

        assertEquals("Os instantes não podem ser nulos", exception.getMessage());
    }

    @Test
    void testDuracaoComApenasMilissegundos() {
        Instant start = Instant.parse("2023-01-01T00:00:00.000Z");
        Instant end = Instant.parse("2023-01-01T00:00:00.050Z");

        String resultado = TimerUtils.duracaoEntreDoisInstants(start, end);

        assertEquals("50 ms", resultado);
    }

    @Test
    void testDuracaoComExatamente1Minuto() {
        Instant start = Instant.parse("2023-01-01T00:00:00.000Z");
        Instant end = Instant.parse("2023-01-01T00:01:00.000Z");

        String resultado = TimerUtils.duracaoEntreDoisInstants(start, end);

        assertEquals("1 minutos 0 ms", resultado);
    }
}