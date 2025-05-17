package dnit.commons.geo;

import dnit.commons.exception.CommonException;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;


/**
 * Classe responsável por calcular a distância entre dois pontos na superfície da Terra
 */
public final class CalculoDistancia {

    private static final double RAIO_TERRA_METROS = 6_371_000;

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
        if (a_latitude == null || a_longitude == null
            || b_latitude == null || b_longitude == null
        ) {
            throw new CommonException("Valores de coordenadas não podem ser nulos");
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
     * Calcula o valor do haversine
     */
    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

}