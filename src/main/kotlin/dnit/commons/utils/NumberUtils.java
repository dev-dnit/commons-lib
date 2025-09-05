package dnit.commons.utils;

import java.util.Arrays;
import java.util.Objects;

public final class NumberUtils {


    private NumberUtils() {
    }


    /**
     * Calcula a média de uma sequência de valores numéricos.
     *
     * @param values Array de valores do tipo Double que serão utilizados no cálculo da média
     * @return A média dos valores fornecidos, ou null se o array estiver vazio/nulo
     */
    public static Double calculoMedia(Double... values) {
        if (values == null || values.length == 0) {
            return null;
        }

        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();
    }

    

    /**
     * Calcula a variância de uma sequência de valores numéricos.
     *
     * @param values Array de valores do tipo Double que serão utilizados no cálculo da variância
     * @return A variância dos valores fornecidos, ou null se o array estiver vazio/nulo
     */
    public static Double calculoVariancia(Double... values) {
        if (values == null || values.length == 0) {
            return null;
        }

        double[] valoresValidos = Arrays.stream(values)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .toArray();

        if (valoresValidos.length == 0) {
            return null;
        }

        double media = Arrays.stream(valoresValidos).average().orElseThrow();

        return Arrays.stream(valoresValidos)
                .map(v -> (v - media) * (v - media))
                .average()
                .orElseThrow();
    }



    /**
     * Calcula o desvio padrão de uma sequência de valores numéricos.
     *
     * @param values Array de valores do tipo Double que serão utilizados no cálculo do desvio padrão
     * @return O desvio padrão dos valores fornecidos, ou null se o array estiver vazio/nulo
     */
    public static Double calculoDesvioPadrao(Double... values) {
        if (values == null || values.length == 0) {
            return null;
        }

        return Math.sqrt(calculoVariancia(values));
    }

}
