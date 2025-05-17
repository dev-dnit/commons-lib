package dnit.commons.sgp;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConceitoTest {

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