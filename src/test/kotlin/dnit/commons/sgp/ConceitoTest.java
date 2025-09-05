package dnit.commons.sgp;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConceitoTest {


    @Nested
    @DisplayName("Deve mapear valores Int válidos para o Conceito correto")
    class FromInt {
        @Test
        void fromNota_deveRetornarConceitoCorreto() {
            assertEquals(Conceito.OTIMO, Conceito.fromNota(5));
            assertEquals(Conceito.BOM, Conceito.fromNota(4));
            assertEquals(Conceito.REGULAR, Conceito.fromNota(3));
            assertEquals(Conceito.RUIM, Conceito.fromNota(2));
            assertEquals(Conceito.PESSIMO, Conceito.fromNota(1));
        }


        @Test
        void fromNota_deveLancarExcecaoParaNotaInvalida() {
            Exception ex = assertThrows(CommonException.class, () -> Conceito.fromNota(0));
            assertEquals("Nota inválida: 0", ex.getMessage());
        }


        @Test
        void fromNota_deveLancarExcecaoParaNotasInvalidas() {
            assertThrows(CommonException.class, () -> Conceito.fromNota(-1));
            assertThrows(CommonException.class, () -> Conceito.fromNota(6));
            assertThrows(CommonException.class, () -> Conceito.fromNota(8));
            assertThrows(CommonException.class, () -> Conceito.fromNota(10));
            assertThrows(CommonException.class, () -> Conceito.fromNota(100));
        }


        @Test
        void fromNota_deveLancarExcecaoParaNotaNula() {
            Exception ex = assertThrows(CommonException.class, () -> Conceito.fromNota(null));
            assertEquals("Nota não pode ser nula", ex.getMessage());
        }


        @Test
        void toConceitoSimplificado_deveRetornarValorCorreto() {
            assertEquals(ConceitoSimplificado.BOM, Conceito.OTIMO.toConceitoSimplificado());
            assertEquals(ConceitoSimplificado.BOM, Conceito.BOM.toConceitoSimplificado());
            assertEquals(ConceitoSimplificado.REGULAR, Conceito.REGULAR.toConceitoSimplificado());
            assertEquals(ConceitoSimplificado.RUIM, Conceito.RUIM.toConceitoSimplificado());
            assertEquals(ConceitoSimplificado.RUIM, Conceito.PESSIMO.toConceitoSimplificado());
        }
    }

    @Nested
    @DisplayName("Deve mapear valores válidos para o Conceito correto")
    class FromDouble {

        @Test
        void deveRetornarConceitoEsperado() {
            // PESSIMO cases
            assertEquals("PESSIMO", Conceito.fromDoubleNota(0.0).name());
            assertEquals("PESSIMO", Conceito.fromDoubleNota(0.5).name());
            assertEquals("PESSIMO", Conceito.fromDoubleNota(1.0).name());
            assertEquals("PESSIMO", Conceito.fromDoubleNota(1.0001).name());

            // RUIM cases
            assertEquals("RUIM", Conceito.fromDoubleNota(1.1).name());
            assertEquals("RUIM", Conceito.fromDoubleNota(1.5).name());
            assertEquals("RUIM", Conceito.fromDoubleNota(2.0).name());
            assertEquals("RUIM", Conceito.fromDoubleNota(2.0001).name());

            // REGULAR cases
            assertEquals("REGULAR", Conceito.fromDoubleNota(2.1).name());
            assertEquals("REGULAR", Conceito.fromDoubleNota(2.5).name());
            assertEquals("REGULAR", Conceito.fromDoubleNota(3.0).name());
            assertEquals("REGULAR", Conceito.fromDoubleNota(3.0001).name());

            // BOM cases
            assertEquals("BOM", Conceito.fromDoubleNota(3.1).name());
            assertEquals("BOM", Conceito.fromDoubleNota(3.5).name());
            assertEquals("BOM", Conceito.fromDoubleNota(4.0).name());
            assertEquals("BOM", Conceito.fromDoubleNota(4.0001).name());

            // OTIMO cases
            assertEquals("OTIMO", Conceito.fromDoubleNota(4.1).name());
            assertEquals("OTIMO", Conceito.fromDoubleNota(4.5).name());
            assertEquals("OTIMO", Conceito.fromDoubleNota(5.0).name());
            assertEquals("OTIMO", Conceito.fromDoubleNota(5.00005).name()); // delta
        }

        @Test
        void deveLancarExcecaoParaNotaNula() {
            CommonException ex = assertThrows(CommonException.class, () -> Conceito.fromDoubleNota(null));
            assertEquals("Nota não pode ser nula", ex.getMessage());
        }

        @Test
        void deveLancarExcecaoParaNotaNegativa() {
            CommonException ex = assertThrows(CommonException.class, () -> Conceito.fromDoubleNota(-0.0001));
            assertEquals("Nota não pode ser negativo", ex.getMessage());
        }

        @Test
        void deveLancarExcecaoParaNotaMaiorQueLimite() {
            CommonException ex = assertThrows(CommonException.class, () -> Conceito.fromDoubleNota(5.02));
            assertEquals("Nota ICS não pode ser maior que 5", ex.getMessage());
        }
    }

}