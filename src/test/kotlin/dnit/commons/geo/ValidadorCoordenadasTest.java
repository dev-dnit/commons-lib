package dnit.commons.geo;

import org.junit.jupiter.api.Test;

import static dnit.commons.geo.ValidadorCoordenadas.estaNoBrasil;
import static dnit.commons.geo.ValidadorCoordenadas.isValidLatitude;
import static dnit.commons.geo.ValidadorCoordenadas.isValidLongitude;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ValidadorCoordenadasTest {

    @Test
    void deveRetornarTrueParaCoordenadasDentroDoBrasil() {
        assertTrue(estaNoBrasil(-15.78, -47.93)); // Brasília
    }


    @Test
    void deveRetornarFalseParaLatitudeForaDosLimites() {
        assertFalse(estaNoBrasil(-40.0, -47.93)); // Latitude muito ao sul
    }


    @Test
    void deveRetornarFalseParaLongitudeForaDosLimites() {
        assertFalse(estaNoBrasil(-15.78, -80.0)); // Longitude muito a oeste
    }


    @Test
    void deveRetornarFalseParaLatitudeENula() {
        assertFalse(estaNoBrasil(null, -47.93));
    }


    @Test
    void deveRetornarFalseParaLongitudeNula() {
        assertFalse(estaNoBrasil(-15.78, null));
    }


    @Test
    void deveRetornarFalseParaLatitudeELongitudeNulas() {
        assertFalse(estaNoBrasil(null, null));
    }


    @Test
    void deveRetornarFalseParaLatitudeELongitudeZeradas() {
        assertFalse(estaNoBrasil(0.0, 0.0));
    }


    @Test
    void deveRetornarTrueParaCoordenadasNaFronteiraSuperior() {
        assertTrue(estaNoBrasil(5.27, -60.0)); // Limite norte
    }


    @Test
    void deveRetornarTrueParaCoordenadasNaFronteiraInferior() {
        assertTrue(estaNoBrasil(-33.75, -60.0)); // Limite sul
    }


    @Test
    void deveRetornarTrueParaCoordenadasNaFronteiraOeste() {
        assertTrue(estaNoBrasil(-10.0, -73.99)); // Limite oeste
    }


    @Test
    void deveRetornarTrueParaCoordenadasNaFronteiraLeste() {
        assertTrue(estaNoBrasil(-10.0, -34.79)); // Limite leste
    }


    @Test
    void deveRetornarFalseParaCoordenadasForaDoBrasilNoCaribe() {
        assertFalse(estaNoBrasil(18.0, -66.0)); // Porto Rico
    }

    @Test
    void testIsValidLatitude_ValidValues() {
        assertTrue(isValidLatitude(0.0));
        assertTrue(isValidLatitude(-89.9));
        assertTrue(isValidLatitude(-90.0));
        assertTrue(isValidLatitude(90.0));
        assertTrue(isValidLatitude(89.9));
    }

    @Test
    void testIsValidLatitude_InvalidValues() {
        assertFalse(isValidLatitude(null));
        assertFalse(isValidLatitude(-90.001));
        assertFalse(isValidLatitude(90.001));
    }

    @Test
    void testIsValidLongitude_ValidValues() {
        assertTrue(isValidLongitude(0.0));
        assertTrue(isValidLongitude(-180.0));
        assertTrue(isValidLongitude(180.0));
    }

    @Test
    void testIsValidLongitude_InvalidValues() {
        assertFalse(isValidLongitude(null));
        assertFalse(isValidLongitude(-180.001));
        assertFalse(isValidLongitude(180.001));
    }

    @Test
    void testIsValidLatitude_ValidValuesInLoop() {
        for (double latitude = -90.0; latitude <= 90.0; latitude += 10.3) {
            assertTrue(isValidLatitude(latitude));
        }
    }

    @Test
    void testIsValidLongitude_ValidValuesInLoop() {
        for (double longitude = -180.0; longitude <= 180.0; longitude += 10.3) {
            assertTrue(isValidLongitude(longitude));
        }
    }

    @Test
    void testNullValueIsInvalid() {
        assertFalse(ValidadorCoordenadas.isValidAltimetria(null), "Valor null deve ser inválido");
    }

    @Test
    void testMinus500ValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(-500.0), "Valor -500 deve ser válido");
    }

    @Test
    void testZeroValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(0.0), "Valor 0 deve ser válido");
    }

    @Test
    void test500ValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(500.0), "Valor 500 deve ser válido");
    }

    @Test
    void test10000ValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(10000.0), "Valor 10000 deve ser válido");
    }

    @Test
    void testBelowMinus500ValueIsInvalid() {
        assertFalse(ValidadorCoordenadas.isValidAltimetria(-501.0), "Valor abaixo de -500 deve ser inválido");
    }

    @Test
    void testAbove10000ValueIsInvalid() {
        assertFalse(ValidadorCoordenadas.isValidAltimetria(10001.0), "Valor acima de 10000 deve ser inválido");
    }

    @Test
    void testEverestValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(8848.0), "Valor do Monte Everest (8848) deve ser válido");
    }

    @Test
    void testDeadSeaValueIsValid() {
        assertTrue(ValidadorCoordenadas.isValidAltimetria(-430.0), "Valor do Mar Morto (-430) deve ser válido");
    }

}