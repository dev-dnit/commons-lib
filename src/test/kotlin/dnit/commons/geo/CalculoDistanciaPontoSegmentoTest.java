package dnit.commons.geo;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import static dnit.commons.geo.CalculoDistancia.distancePontoAoSegmento;
import static org.junit.jupiter.api.Assertions.*;

class CalculoDistanciaPontoSegmentoTest {

    private static final double TOLERANCIA_METROS = 0.001; // tolerância numérica

    @Test
    void deveCalcularCorretamenteDistanciasEdgeCase() {
        // Latitude inválida
        var distancia1 = distancePontoAoSegmento(-22.79392490, -43.02873523, -22.79431152, -43.0303955099999, -22.79370117, -43.027771);
        var distancia2 = distancePontoAoSegmento(-22.79392490, -43.02873523, -22.79370117, -43.027771, -22.79431152, -43.0303955099999);

        assertEquals(distancia1, distancia2, TOLERANCIA_METROS);
        assertEquals(0.0, distancia2, TOLERANCIA_METROS);
    }

    // ---------- Casos básicos e de validação ----------

    @Test
    void deveLancarExcecaoQuandoCoordenadasInvalidas() {
        // Latitude inválida
        assertThrows(CommonException.class, () -> distancePontoAoSegmento( 91, 0, 0, 0, 10, 0));
        assertThrows(CommonException.class, () -> distancePontoAoSegmento(  0, 0, 91, 0, 10, 0));
        assertThrows(CommonException.class, () -> distancePontoAoSegmento(  0, 0, 0, 0, 91, 0));

        // Longitude inválida
        assertThrows(CommonException.class, () -> distancePontoAoSegmento(0, 181, 0, 0, 10, 0));
        assertThrows(CommonException.class, () -> distancePontoAoSegmento(0,   0, 0, 181, 10, 0));
        assertThrows(CommonException.class, () -> distancePontoAoSegmento(0,   0, 0,   0, 10, 181));
    }


    // ---------- Cenários geométricos no globo (usando equador/meridiano) ----------


    @Test
    void calculoDistanciaBaseadoEmValoresEmpiricos1() {
        double esperado = 10.41566;
        double d1 = distancePontoAoSegmento(-16.694331, -49.323430, -16.694870, -49.324477, -16.693729, -49.322641);
        double d2 = distancePontoAoSegmento(-16.694331, -49.323430, -16.693729, -49.322641, -16.694870, -49.324477);
        assertEquals(esperado, d1, 0.01);
        assertEquals(esperado, d2, 0.01);
    }


    @Test
    void calculoDistanciaBaseadoEmValoresEmpiricos2() {
        double esperado = 67.0;
        double d1 = distancePontoAoSegmento(-15.797809, -47.868980, -15.793052, -47.882113, -15.799438, -47.861774);
        double d2 = distancePontoAoSegmento(-15.797809, -47.868980, -15.799438, -47.861774, -15.793052, -47.882113);
        assertEquals(esperado, d1, 0.5);
        assertEquals(esperado, d2, 0.5);
    }


    @Test
    void calculoDistanciaBaseadoEmValoresEmpiricos3() {
        double esperado = 24.0567;
        double d1 = distancePontoAoSegmento(-31.569010, -53.388029, -31.575607, -53.386780, -31.568505, -53.387849);
        double d2 = distancePontoAoSegmento(-31.569010, -53.388029, -31.568505, -53.387849, -31.575607, -53.386780);
        assertEquals(esperado, d1, 0.1);
        assertEquals(esperado, d2, 0.1);
    }


    @Test
    void calculoDistanciaBaseadoEmValoresEmpiricos4() {
        double esperado = 14.54;
        double d1 = distancePontoAoSegmento(-31.571401, -53.387568, -31.575607, -53.386780, -31.568505, -53.387849);
        double d2 = distancePontoAoSegmento(-31.571401, -53.387568, -31.568505, -53.387849, -31.575607, -53.386780);
        assertEquals(esperado, d1, 0.1);
        assertEquals(esperado, d2, 0.1);
    }


    @Test
    void pontoSobreSegmentoNoEquador() {
        // Segmento: no Equador, de lon 0° a 10°, lat = 0°
        // Ponto: lat = 0°, lon = 5° (exatamente no segmento)
        double d = distancePontoAoSegmento(0, 5, 0, 0, 0, 10);
        assertEquals(0.0, d, TOLERANCIA_METROS);
    }


    @Test
    void pontoProximoAoSegmentoNoEquador_DistanciaPerpendicular() {
        // Segmento: Equador de 0° a 10° de longitude
        // Ponto: 1° ao norte do Equador, na projeção de 5°E (lat=1°, lon=5°)
        // Distância esperada ~ 1° em radianos * R
        double esperado = Math.toRadians(1.0) * CalculoDistancia.RAIO_TERRA_METROS; // ~111.195 km
        double d = distancePontoAoSegmento(1, 5, 0, 0, 0, 10);
        assertEquals(esperado, d, 1.0); // tolerância um pouco maior para operações trigonométricas
    }


    @Test
    void pontoForaDaExtensaoDoSegmento_UsaEndpointMaisProximo() {
        // Segmento: Equador de lon 0° a 10°
        // Ponto: Equador em lon 20° (fora do segmento, mais próximo do endpoint em 10°)
        double esperado = Math.toRadians(10.0) * CalculoDistancia.RAIO_TERRA_METROS; // 10° ao longo do Equador
        double d = distancePontoAoSegmento(0, 20, 0, 0, 0, 10);
        assertEquals(esperado, d, 1.0);
    }


    @Test
    void segmentoDegenerado_UsaDistanciaAteOPonto() {
        // Segmento degenerado (A == B)
        // Distância deve ser igual à distância geodésica entre P(0°,1°) e A(0°,0°) ~ 1°
        double esperado = Math.toRadians(1.0) * CalculoDistancia.RAIO_TERRA_METROS;
        double d = distancePontoAoSegmento(0, 1, 0, 0, 0, 0);
        assertEquals(esperado, d, 1.0);
    }


    @Test
    void pontoProximoAoMeridiano_SegmentoDeMeridiano() {
        // Segmento: Meridiano de lon 0°, de lat 0° a 10°
        // Ponto: Equador em lon 5° (projeção cai fora do segmento -> endpoint A(0°,0°))
        double esperado = Math.toRadians(5.0) * CalculoDistancia.RAIO_TERRA_METROS; // 5° ao longo do Equador
        double d = distancePontoAoSegmento(0, 5, 0, 0, 10, 0);
        assertEquals(esperado, d, 1.0);
    }

}
