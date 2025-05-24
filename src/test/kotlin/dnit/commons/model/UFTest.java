package dnit.commons.model;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UFTest {

    @Test
    void deveTestarTodosOsCodigosIbge() {
        List<Integer> codigosIbge = Arrays.asList(
                11, 12, 13, 14, 15, 16, 17, // NORTE
                21, 22, 23, 24, 25, 26, 27, 28, 29, // NORDESTE
                31, 32, 33, 35, // SUDESTE
                41, 42, 43, // SUL
                50, 51, 52, 53 // CENTRO_OESTE
        );

        for (int codigoIbge : codigosIbge) {
            UF uf = UF.ufFromCodigoIbge(codigoIbge);
            assertTrue(UF.isUfValida(uf.sigla));
            assertEquals(codigoIbge, uf.codigoIbge, "Código IBGE inválido para a UF " + uf.nomeCompleto);
        }
    }


    @Test
    void deveTestarTodasAsSiglas() {
        List<String> siglas = Arrays.asList(
                "RO", "AC", "AM", "RR", "PA", "AP", "TO", // NORTE
                "MA", "PI", "CE", "RN", "PB", "PE", "AL", "SE", "BA", // NORDESTE
                "MG", "ES", "RJ", "SP", // SUDESTE
                "PR", "SC", "RS", // SUL
                "MS", "MT", "GO", "DF" // CENTRO_OESTE
        );

        for (String sigla : siglas) {
            UF uf = UF.ufFromSigla(sigla);
            assertTrue(UF.isUfValida(sigla));
            assertEquals(sigla.toUpperCase(), uf.sigla, "Sigla inválida para a UF " + uf.nomeCompleto);
        }

        // Verificar o tamanho da lista de siglas
        assertEquals(siglas.size(), UF.values().length, "O número de siglas não corresponde ao número de UFs");
    }


    @Test
    void deveLancarExcecaoParaCodigoIbgeNulo() {
        assertThrows(CommonException.class, () -> {
            UF.ufFromCodigoIbge(null);
        });
    }


    @Test
    void deveLancarExcecaoParaCodigoIbgeInvalido() {
        CommonException exception = assertThrows(CommonException.class, () -> {
            UF.ufFromCodigoIbge(99);
        });
        assertEquals("Código IBGE inválido: 99", exception.getMessage());
    }


    @Test
    void deveLancarExcecaoParaSiglaInvalida() {
        CommonException exception = assertThrows(CommonException.class, () -> {
            UF.ufFromSigla("xx");
        });
        assertEquals("Sigla inválida: xx", exception.getMessage());
    }


    @Test
    void deveRetornarTrueSeSiglaValida() {
        assertTrue(UF.isUfValida("RJ"));
    }


    @Test
    void deveRetornarFalseSeNaoForUpperCase() {
        assertFalse(UF.isUfValida("rj"));
        assertFalse(UF.isUfValida("rJ"));
        assertFalse(UF.isUfValida("Rj"));
    }


    @Test
    void deveRetornarFalseSeSiglaInvalida() {
        assertFalse(UF.isUfValida(" RJ"));
        assertFalse(UF.isUfValida("RJ "));
        assertFalse(UF.isUfValida("zz"));
        assertFalse(UF.isUfValida(null));
        assertFalse(UF.isUfValida(""));
    }


    @Test
    void todosOsValoresDevemTerCorrespondenciaComCodigoIbge() {
        for (UF uf : UF.values()) {
            assertEquals(uf, UF.ufFromCodigoIbge(uf.codigoIbge));
        }
    }


    @Test
    void todosOsValoresDevemTerCorrespondenciaComSigla() {
        for (UF uf : UF.values()) {
            assertEquals(uf, UF.ufFromSigla(uf.sigla));
        }
    }

}