package dnit.commons.geo;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import static dnit.commons.geo.CalculoDistancia.distanciaEmMetros;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculoDistanciaTest {

    private static final double TOLERANCIA_METROS = 0.0001;


    @Test
    void deveRetornarExcecaoQuandoCoordenadasSaoNulas() {
        assertThrows(CommonException.class, () -> distanciaEmMetros(null, null, null, null));
        assertThrows(CommonException.class, () -> distanciaEmMetros(52.2296756, 21.0122287, 52.2296756, null));
        assertThrows(CommonException.class, () -> distanciaEmMetros(52.2296756, 21.0122287, null, null));
        assertThrows(CommonException.class, () -> distanciaEmMetros(52.2296756, null, 52.2296756, 21.0122287));
        assertThrows(CommonException.class, () -> distanciaEmMetros(null, 21.0122287, 52.2296756, 21.0122287));
    }


    @Test
    void calculaDistanciaEntreDoisPontosNaMesmaCoordenada() {
        validateDistance(52.2296756, 21.0122287, 52.2296756, 21.0122287, 0);
        validateDistance(-52.2296756, 21.0122287, -52.2296756, 21.0122287, 0);
        validateDistance(52.2296756, -21.0122287, 52.2296756, -21.0122287, 0);
        validateDistance(-52.2296756, -21.0122287, -52.2296756, -21.0122287, 0);
    }


    @Test
    void calculaDistanciaEntreDoisPontosMuitoPerto() {
        validateDistance(15.7459130885759, 47.92028000749978, 15.745997634582396, 47.920316217321755, 10.168483467901334);
        validateDistance(-15.7459130885759, 47.92028000749978, -15.745997634582396, 47.920316217321755, 10.168483467901334);
        validateDistance(15.7459130885759, -47.92028000749978, 15.745997634582396, -47.920316217321755, 10.168483467901334);
        validateDistance(-15.7459130885759, -47.92028000749978, -15.745997634582396, -47.920316217321755, 10.168483467901334);
    }


    @Test
    void calculaDistanciaEntreDoisPontosPerto() {
        validateDistance(15.74599789909137, 47.92031370486275, 15.745212585903793, 47.91986407123677, 99.70405828456956);
        validateDistance(-15.74599789909137, 47.92031370486275, -15.745212585903793, 47.91986407123677, 99.70405828456956);
        validateDistance(15.74599789909137, -47.92031370486275, 15.745212585903793, -47.91986407123677, 99.70405828456956);
        validateDistance(-15.74599789909137, -47.92031370486275, -15.745212585903793, -47.91986407123677, 99.70405828456956);
    }


    @Test
    void calculaDistanciaEntreDoisPontosProximos() {
        validateDistance(52.22, 21.01, 52.22, 21.015, 340.6074067571155);
        validateDistance(-52.22, 21.01, -52.22, 21.015, 340.6074067571155);
        validateDistance(52.22, -21.01, 52.22, -21.015, 340.6074067571155);
        validateDistance(-52.22, -21.01, -52.22, -21.015, 340.6074067571155);
    }


    @Test
    void calculaDistanciaEntreDoisPontosDistanciaMediana() {
        validateDistance(52.2296756, 21.0122287, 52.406374, 16.9251681, 278458.17507541943);
        validateDistance(-52.2296756, 21.0122287, -52.406374, 16.9251681, 278458.17507541943);
        validateDistance(52.2296756, -21.0122287, 52.406374, -16.9251681, 278458.17507541943);
        validateDistance(-52.2296756, -21.0122287, -52.406374, -16.9251681, 278458.17507541943);
    }


    private void validateDistance(
            double a_latitude, double a_longitude,
            double b_latitude, double b_longitude,
            double expectedValue
    ) {
        assertEquals(expectedValue,
                distanciaEmMetros(a_latitude, a_longitude, b_latitude, b_longitude),
                TOLERANCIA_METROS);
    }


}