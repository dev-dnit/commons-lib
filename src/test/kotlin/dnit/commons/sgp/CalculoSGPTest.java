package dnit.commons.sgp;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dnit.commons.sgp.CalculoSGP.convertIGGtoSci;
import static dnit.commons.sgp.CalculoSGP.convertIRItoPSI;
import static dnit.commons.sgp.CalculoSGP.convertPSItoIRI;
import static dnit.commons.sgp.CalculoSGP.convertSCItoIGG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculoSGPTest {

    private static final double DELTA = 0.0001; // Allowing minor floating-point differences

    @Nested
    class IRItoPSI {

        @Test
        void shouldConvertIRItoPSI() {
            double[][] testCases = {
                    {100.0, 0.0},
                    {50.0, 0.0005634279},
                    {25.0, 0.053077},
                    {10.0, 0.811603},
                    {7.00, 1.400334},
                    {5.00, 2.014452},
                    {3.50, 2.646067},
                    {2.00, 3.475720},
                    {1.00, 4.168765},
                    {0.50, 4.565504},
                    {0.10, 4.909912},
                    {0.01, 4.990917},
                    {0.001, 4.999091},
                    {0.0, 5.},
            };

            for (double[] testCase : testCases) {
                double iri = testCase[0];
                double psi = testCase[1];

                assertEquals(psi, convertIRItoPSI(iri), DELTA);

                if (psi > 0) {
                    assertEquals(iri, convertPSItoIRI(psi), DELTA);
                }

                if (iri > 0 && iri < 100) {
                    assertEquals(iri, convertPSItoIRI(convertIRItoPSI(iri)), DELTA);
                }
            }
        }


        @Test
        void shouldThrowExceptionWhenIRIIsNull() {
            assertThrows(CommonException.class, () -> convertIRItoPSI(null));
        }


        @Test
        void shouldThrowExceptionWhenPSIIsNull() {
            assertThrows(CommonException.class, () -> convertPSItoIRI(null));
        }

    }


    @Nested
    class IGGtoSCI {

        @Test
        void shouldConvertIGGtoSci() {
            double[][] testCases = {
                    {800.0, 0.0},
                    {500.0, 0.0021714212486028633},
                    {400.0, 0.136020},
                    {200.0, 0.710423},
                    {120.0, 1.293966},
                    {80.00, 1.832577},
                    {40.00, 2.794274},
                    {20.00, 3.627633},
                    {10.00, 4.218306},
                    {5.00, 4.579917},
                    {1.00, 4.910636},
                    {0.10, 4.990934},
                    {0.01, 4.999092},
                    {0.0, 5.0}
            };

            for (double[] testCase : testCases) {
                double igg = testCase[0];
                double sci = testCase[1];

                assertEquals(sci, convertIGGtoSci(igg), DELTA);

                if (sci > 0) {
                    assertEquals(igg, convertSCItoIGG(sci), DELTA);
                }

                if (igg > 0 && igg < 800) {
                    assertEquals(igg, convertSCItoIGG(convertIGGtoSci(igg)), DELTA);
                }
            }
        }


        @Test
        void deveLancarExcecaoParaIGGNulo() {
            assertThrows(CommonException.class, () -> convertIGGtoSci(null));
        }


        @Test
        void deveLancarExcecaoParaSCINulo() {
            assertThrows(CommonException.class, () -> convertSCItoIGG(null));
        }
    }


    @Nested
    class ConceitoICS {

        @Test
        void deveRetornarPessimoParaICSMenorOuIgualA1() {
            assertEquals(Conceito.PESSIMO, CalculoSGP.getConceitoICS(1.0));
        }


        @Test
        void deveRetornarRuimParaICSEntre1EMenorOuIgualA2() {
            assertEquals(Conceito.RUIM, CalculoSGP.getConceitoICS(2.0));
        }


        @Test
        void deveRetornarRegularParaICSEntre2EMenorOuIgualA3() {
            assertEquals(Conceito.REGULAR, CalculoSGP.getConceitoICS(3.0));
        }


        @Test
        void deveRetornarBomParaICSEntre3EMenorOuIgualA4() {
            assertEquals(Conceito.BOM, CalculoSGP.getConceitoICS(4.0));
        }


        @Test
        void deveRetornarOtimoParaICSEntre4EMenorOuIgualA5() {
            assertEquals(Conceito.OTIMO, CalculoSGP.getConceitoICS(5.0));
        }


        @Test
        void deveRetornarOtimoParaICSComPequenoDeltaMaiorQue5() {
            assertEquals(Conceito.OTIMO, CalculoSGP.getConceitoICS(5.009));
        }


        @Test
        void deveLancarExcecaoParaICSNulo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoICS(null));
        }


        @Test
        void deveLancarExcecaoParaICSNegativo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoICS(-1.0));
        }


        @Test
        void deveLancarExcecaoParaICSZero() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoICS(0.0));
        }


        @Test
        void deveLancarExcecaoParaICSMaiorQue5ComDelta() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoICS(5.02));
        }
    }


    @Nested
    class ConceitoIGG {

        @Test
        void deveRetornarPessimoParaIGGAltos() {
            assertEquals(Conceito.PESSIMO, CalculoSGP.getConceitoIGG(500.0));
        }


        @Test
        void deveRetornarPessimoParaIGGMaiorQue160() {
            assertEquals(Conceito.PESSIMO, CalculoSGP.getConceitoIGG(161.0));
        }


        @Test
        void deveRetornarRuimParaIGGEntre81EMenorOuIgualA160() {
            assertEquals(Conceito.RUIM, CalculoSGP.getConceitoIGG(100.0));
        }


        @Test
        void deveRetornarRegularParaIGGEntre41EMenorOuIgualA80() {
            assertEquals(Conceito.REGULAR, CalculoSGP.getConceitoIGG(50.0));
        }


        @Test
        void deveRetornarBomParaIGGEntre21EMenorOuIgualA40() {
            assertEquals(Conceito.BOM, CalculoSGP.getConceitoIGG(30.0));
        }


        @Test
        void deveRetornarOtimoParaIGGMenorOuIgualA20() {
            assertEquals(Conceito.OTIMO, CalculoSGP.getConceitoIGG(20.0));
        }


        @Test
        void deveRetornarOtimoParaIGGMenorOuIgualA0() {
            assertEquals(Conceito.OTIMO, CalculoSGP.getConceitoIGG(0.0));
        }


        @Test
        void deveLancarExcecaoParaIGGNulo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoIGG(null));
        }


        @Test
        void deveLancarExcecaoParaIGGNegativo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoIGG(-1.0));
        }

    }


    @Nested
    class ConceitoIRI {
        @Test
        void deveRetornarPessimoParaIRIMaiorQue6() {
            assertEquals(Conceito.PESSIMO, CalculoSGP.getConceitoIRI(6.1));
        }


        @Test
        void deveRetornarRuimParaIRIEntre4_5EMenorOuIgualA6() {
            assertEquals(Conceito.RUIM, CalculoSGP.getConceitoIRI(5.0));
        }


        @Test
        void deveRetornarRegularParaIRIEntre3_5EMenorOuIgualA4_5() {
            assertEquals(Conceito.REGULAR, CalculoSGP.getConceitoIRI(4.0));
        }


        @Test
        void deveRetornarBomParaIRIEntre2_5EMenorOuIgualA3_5() {
            assertEquals(Conceito.BOM, CalculoSGP.getConceitoIRI(3.0));
        }


        @Test
        void deveRetornarOtimoParaIRIMenorOuIgualA2_5() {
            assertEquals(Conceito.OTIMO, CalculoSGP.getConceitoIRI(2.5));
        }


        @Test
        void deveLancarExcecaoParaIRINulo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoIRI(null));
        }


        @Test
        void deveLancarExcecaoParaIRINegativo() {
            assertThrows(CommonException.class, () -> CalculoSGP.getConceitoIRI(-1.0));
        }
    }

}