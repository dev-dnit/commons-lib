package dnit.commons.geo;

/**
 * Classe utilitária por validar se coordenadas geográficas estão no território brasileiro.
 */
public final class ValidadorCoordenadas {

    private ValidadorCoordenadas() { }


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
        if (latitude == null || longitude == null) {
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
