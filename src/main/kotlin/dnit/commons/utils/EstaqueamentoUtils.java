package dnit.commons.utils;

import dnit.commons.model.Estaca;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class EstaqueamentoUtils {

    private EstaqueamentoUtils() { }



    /**
     * Method auxiliar para arredondar o valor para o inicio do intervalo
     */
    public static double currentInterval(final double value, final double intervalo) {
        return nextInterval(value, intervalo) - intervalo;
    }



    /**
     * Method auxiliar para arredondar o valor para o próximo intervalo
     * Arredonda para cima (positivo) o valor mais próximo do intervalo
     */
    public static double nextInterval(final double value, final double intervalo) {
        final double nearestNextCeiledValue = Math.ceil(value / intervalo) * intervalo;
        final double epsilon = 0.00001; // 1e-5 - Valor de tolerância para comparação.

        if (value + epsilon >= nearestNextCeiledValue) {
            // Exacly on the interval. Return the next value
            return value + intervalo;
        }

        return nearestNextCeiledValue; // Está dentro do intervalo. Retorne o valor arredondado
    }



    /**
     * Realiza o estaqueamento de um conjunto de itens.
     */
    public static <T> List<Estaca<T>> realizaEstaqueamento(
            final double intervalo,
            final List<T> items,
            final Function<T, Double> doubleExtractor
    ) {
        List<Estaca<T>> estaqueamentos = new ArrayList<>();
        final double epsilon = 0.00001; // 1e-5 - Valor de tolerância para comparação.

        if (CollectionUtils.isNullOrEmpty(items)) {
            return estaqueamentos;
        }

        Estaqueador<T> estaqueador = new Estaqueador<>();
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;

        for (T item : items) {
            double value = doubleExtractor.apply(item);
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
            estaqueador.insert(value, item);
        }

        for (double inicio = currentInterval(minValue, intervalo);
             inicio <= nextInterval(maxValue, intervalo) - epsilon;
             inicio += intervalo
        ) {
            double fim = nextInterval(inicio, intervalo);

            // We make fim - epsilon to avoid the case where the interval is exactly the same
            List<T> itemsNoIntervalo = estaqueador.retrieveInRange(inicio, fim - epsilon);

            if (CollectionUtils.isNullOrEmpty(itemsNoIntervalo)) {
                continue;
            }

            estaqueamentos.add(new Estaca<>(inicio, fim, itemsNoIntervalo));
        }

        return estaqueamentos;
    }

}
