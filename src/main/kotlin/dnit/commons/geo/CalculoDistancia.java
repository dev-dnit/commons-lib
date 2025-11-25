package dnit.commons.geo;

import dnit.commons.exception.CommonException;

import static dnit.commons.algebra.VectorOperations.cross;
import static dnit.commons.algebra.VectorOperations.dot;
import static dnit.commons.algebra.VectorOperations.norm;
import static dnit.commons.geo.ValidadorCoordenadas.isValidLatitude;
import static dnit.commons.geo.ValidadorCoordenadas.isValidLongitude;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;


/**
 * Classe responsável por calcular a distância entre dois pontos na superfície da Terra
 */
public final class CalculoDistancia {

    public static final double RAIO_TERRA_METROS = 6_371_000;


    private CalculoDistancia() {  }


    /**
     * Calcula a distância entre dois pontos na superfície da Terra usando a fórmula de Haversine
     * Obs: Os Valores de distância não são exatos, mas são suficientemente precisos,
     * com ordem de precisão de +-1 metro.
     * @see "https://en.wikipedia.org/wiki/Haversine_formula"
     */
    public static double distanciaEmMetros(
            Double a_latitude, Double a_longitude,
            Double b_latitude, Double b_longitude
    ) {
        if (!isValidLatitude(a_latitude)
            || !isValidLatitude(b_latitude)
            || !isValidLongitude(a_longitude)
            || !isValidLongitude(b_longitude)
        ) {
            throw new CommonException("Valores de coordenadas não podem ser inválidas");
        }

        double deltaLatitude = toRadians(b_latitude - a_latitude);
        double deltaLongitude = toRadians(b_longitude - a_longitude);

        double alfa = haversine(deltaLatitude)
                     + (cos(toRadians(a_latitude))
                                * cos(toRadians(b_latitude))
                                * haversine(deltaLongitude)
        );

        double coeficiente = 2 * atan2(sqrt(alfa), sqrt(1 - alfa));

        return RAIO_TERRA_METROS * coeficiente;
    }



    /**
     * Distância mínima entre um ponto (lat,lon) e o segmento geodésico
     * entre (lat1,lon1) e (lat2,lon2) na superfície da Terra.
     */
    public static double distancePontoAoSegmento(
            double lat, double lon,
            double lat1, double lon1,
            double lat2, double lon2
    ) {
        if (!isValidLatitude(lat) || !isValidLatitude(lat1)  || !isValidLatitude(lat2)
            || !isValidLongitude(lon) || !isValidLongitude(lon1) || !isValidLongitude(lon2)
        ) {
            throw new CommonException("Valores de coordenadas não podem ser inválidas");
        }

        double[] p = toVector(lat, lon);
        double[] a = toVector(lat1, lon1);
        double[] b = toVector(lat2, lon2);

        // Vetor normal do grande círculo definido por A→B
        double[] gcNormal = cross(a, b);
        double normGC = norm(gcNormal);

        // Se A e B são praticamente o mesmo ponto, usa distância direta
        if (normGC < 1e-12) {
            return distanciaEmMetros(lat, lon, lat1, lon1);
        }

        // Projeção do ponto P no plano do grande círculo
        double[] pOnGC = cross(gcNormal, p);
        double[] closest = cross(pOnGC, gcNormal);

        // Normaliza
        double len = norm(closest);
        double[] c = { closest[0]/len, closest[1]/len, closest[2]/len };

        // Verifica se C está entre A e B (em termos de ângulo)
        double angleAC = Math.acos(clamp(dot(a, c), -1.0, 1.0));
        double angleCB = Math.acos(clamp(dot(c, b), -1.0, 1.0));
        double angleAB = Math.acos(clamp(dot(a, b), -1.0, 1.0));

        if (angleAC + angleCB - angleAB < 1e-8) {
            // C está no segmento: distância P–C
            double anglePC = Math.acos(clamp(dot(p, c), -1.0, 1.0));
            return anglePC * RAIO_TERRA_METROS;
        }

        // C está fora do segmento → usar menor distância a um dos vértices
        double d1 = Math.acos(clamp(dot(p, a), -1.0, 1.0)) * RAIO_TERRA_METROS;
        double d2 = Math.acos(clamp(dot(p, b), -1.0, 1.0)) * RAIO_TERRA_METROS;

        return Math.min(d1, d2);
    }



    /**
     * Calcula o valor do haversine
     */
    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }



    // Helper method to clamp values
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }



    // Converte lat/lon para vetor 3D normalizado
    private static double[] toVector(double latDeg, double lonDeg) {
        double lat = Math.toRadians(latDeg);
        double lon = Math.toRadians(lonDeg);

        return new double[] {
                Math.cos(lat) * Math.cos(lon),
                Math.cos(lat) * Math.sin(lon),
                Math.sin(lat)
        };
    }


}