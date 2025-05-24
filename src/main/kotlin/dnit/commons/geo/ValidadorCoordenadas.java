package dnit.commons.geo;

/**
 * Classe utilitária por validar se coordenadas geográficas estão no território brasileiro.
 */
public final class ValidadorCoordenadas {

    private ValidadorCoordenadas() { }


    /**
     * Válida se a latitude é valida.
     */
    public static boolean isValidLatitude(Double latitude) {
        if (latitude == null) {
            return false;
        }

        return latitude >= -90 && latitude <= 90;
    }



    /**
     * Valida se longitude é valida
     */
    public static boolean isValidLongitude(Double longitude) {
        if (longitude == null) {
            return false;
        }

        return longitude >= -180 && longitude <= 180;
    }



    /**
     * Valida se altitude é valida
     */
    public static boolean isValidAltimetria(Double altitude) {
        if (altitude == null) {
            return false;
        }

        if (altitude < -500) {
            return false; // Valor do mar morto = −430
        }

        if (altitude > 10_000) {
            return false; // Valor do monte Everest = 8848
        }

        return true;

    }



    /**
     * Verifica se a latitude e longitude informadas estão dentro dos limites geográficos do Brasil.
     * Essa verificação utiliza uma abordagem simplificada com uma caixa delimitadora retangular,
     * sem considerar as irregularidades reais da fronteira brasileira.
     *
     * @param latitude  Latitude em graus decimais. Valores negativos indicam o hemisfério sul.
     * @param longitude Longitude em graus decimais. Valores negativos indicam o hemisfério oeste.
     * @return {@code true} se as coordenadas estiverem dentro da área delimitada do Brasil;
     *         {@code false} caso contrário.
     */
    public static boolean estaNoBrasil(Double latitude, Double longitude) {
        if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
            return false;
        }

        if (longitude >= -32.38) {
            return false; // À direita de FE.
        }

        else if (longitude <= -74.20) {
            return false; // À esquerda do Brasil.
        }

        else if (latitude <= -33.77) {
            return false; // Em baixo do Brasil.
        }

        else if (latitude >= 5.40) {
            return false; // Acima do Brasil.
        }

        else if (longitude >= -34.75 && latitude <= -3.90) {
            return false; // A direita de PE mas a baixo de FE.
        }

        // A direita de PE mas a baixo de FE.
        else return true;
    }

}
