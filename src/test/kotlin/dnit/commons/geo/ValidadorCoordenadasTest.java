package dnit.commons.geo;

import org.junit.jupiter.api.Test;

import static dnit.commons.geo.ValidadorCoordenadas.estaNoBrasil;
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

}