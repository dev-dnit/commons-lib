package dnit.commons.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RegiaoTest {

    @Test
    void deveMaperRegiaoParaUF() {
        assertEquals(Regiao.NORTE, UF.AC.regiao);
        assertEquals(Regiao.NORDESTE, UF.AL.regiao);
        assertEquals(Regiao.NORTE, UF.AP.regiao);
        assertEquals(Regiao.NORTE, UF.AM.regiao);
        assertEquals(Regiao.NORDESTE, UF.BA.regiao);
        assertEquals(Regiao.NORDESTE, UF.CE.regiao);
        assertEquals(Regiao.CENTRO_OESTE, UF.DF.regiao);
        assertEquals(Regiao.SUDESTE, UF.ES.regiao);
        assertEquals(Regiao.CENTRO_OESTE, UF.GO.regiao);
        assertEquals(Regiao.NORDESTE, UF.MA.regiao);
        assertEquals(Regiao.CENTRO_OESTE, UF.MT.regiao);
        assertEquals(Regiao.CENTRO_OESTE, UF.MS.regiao);
        assertEquals(Regiao.SUDESTE, UF.MG.regiao);
        assertEquals(Regiao.NORTE, UF.PA.regiao);
        assertEquals(Regiao.NORDESTE, UF.PB.regiao);
        assertEquals(Regiao.SUL, UF.PR.regiao);
        assertEquals(Regiao.NORDESTE, UF.PE.regiao);
        assertEquals(Regiao.NORDESTE, UF.PI.regiao);
        assertEquals(Regiao.SUDESTE, UF.RJ.regiao);
        assertEquals(Regiao.NORDESTE, UF.RN.regiao);
        assertEquals(Regiao.SUL, UF.RS.regiao);
        assertEquals(Regiao.NORTE, UF.RO.regiao);
        assertEquals(Regiao.NORTE, UF.RR.regiao);
        assertEquals(Regiao.SUL, UF.SC.regiao);
        assertEquals(Regiao.SUDESTE, UF.SP.regiao);
        assertEquals(Regiao.NORDESTE, UF.SE.regiao);
        assertEquals(Regiao.NORTE, UF.TO.regiao);
    }


    @Test
    void deveRetornarTodasAsUfsDaRegiaoNorte() {
        List<UF> ufs = Regiao.NORTE.ufsDaRegiao();
        Set<UF> esperado = new HashSet<>(Arrays.asList(UF.AC, UF.AP, UF.AM, UF.PA, UF.RO, UF.RR, UF.TO));
        assertEquals(esperado, new HashSet<>(ufs));
    }


    @Test
    void deveRetornarTodasAsUfsDaRegiaoNordeste() {
        List<UF> ufs = Regiao.NORDESTE.ufsDaRegiao();
        Set<UF> esperado = new HashSet<>(Arrays.asList(UF.AL, UF.BA, UF.CE, UF.MA, UF.PB, UF.PE, UF.PI, UF.RN, UF.SE));
        assertEquals(esperado, new HashSet<>(ufs));
    }


    @Test
    void deveRetornarTodasAsUfsDaRegiaoCentroOeste() {
        List<UF> ufs = Regiao.CENTRO_OESTE.ufsDaRegiao();
        Set<UF> esperado = new HashSet<>(Arrays.asList(UF.DF, UF.GO, UF.MT, UF.MS));
        assertEquals(esperado, new HashSet<>(ufs));
    }


    @Test
    void deveRetornarTodasAsUfsDaRegiaoSudeste() {
        List<UF> ufs = Regiao.SUDESTE.ufsDaRegiao();
        Set<UF> esperado = new HashSet<>(Arrays.asList(UF.ES, UF.MG, UF.RJ, UF.SP));
        assertEquals(esperado, new HashSet<>(ufs));
    }


    @Test
    void deveRetornarTodasAsUfsDaRegiaoSul() {
        List<UF> ufs = Regiao.SUL.ufsDaRegiao();
        Set<UF> esperado = new HashSet<>(Arrays.asList(UF.PR, UF.RS, UF.SC));
        assertEquals(esperado, new HashSet<>(ufs));
    }

    @Test
    void todosAsRegioesDevemRetornarValida() {
        for (Regiao regiao : Regiao.values()) {
            assertTrue(Regiao.isRegiaoValida(regiao.nomeCompleto));
        }
    }

    @Test
    void deveRetornarTrueParaRegiaoValida() {
        assertTrue(Regiao.isRegiaoValida("Sudeste"));
    }

    @Test
    void deveRetornarFalseParaRegiaoInvalida() {
        assertFalse(Regiao.isRegiaoValida("Atlantico"));
    }

    @Test
    void deveRetornarFalseParaStringVazia() {
        assertFalse(Regiao.isRegiaoValida(""));
    }

    @Test
    void deveRetornarFalseParaNulo() {
        assertFalse(Regiao.isRegiaoValida(null));
    }

}