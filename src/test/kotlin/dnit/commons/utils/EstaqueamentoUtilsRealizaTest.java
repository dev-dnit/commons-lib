package dnit.commons.utils;

import dnit.commons.model.Estaca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for EstaqueamentoUtils.realizaEstaqueamento")
class EstaqueamentoUtilsRealizaTest {

    @Test
    @DisplayName("Returns empty list when input items are null or empty")
    void emptyInputs() {
        assertTrue(EstaqueamentoUtils.realizaEstaqueamento(10.0, new ArrayList<>(), (Double d) -> d).isEmpty());
        assertTrue(EstaqueamentoUtils.realizaEstaqueamento(10.0, null, (Double d) -> d).isEmpty());
    }

    @Test
    @DisplayName("Single item yields a single interval that encloses it")
    void singleItem() {
        List<Double> items = List.of(12.3);
        List<Estaca<Double>> estacas = EstaqueamentoUtils.realizaEstaqueamento(10.0, items, d -> d);

        // intervals: [10,20) and [20,30) because nextInterval(max)=20 and loop goes <= next(max)
        // But only first interval should contain the item
        assertEquals(1, estacas.size());

        Estaca<Double> first = estacas.getFirst();
        assertEquals(10.0, first.inicio(), 1e-9);
        assertEquals(20.0, first.fim(), 1e-9);
        assertEquals(List.of(12.3), first.items());
    }

    @Test
    @DisplayName("Builds contiguous intervals from current(min) to next(max) inclusive and buckets items correctly")
    void bucketsItemsCorrectly() {
        List<Item> items = List.of(
                new Item("A", -120.1), // -130, -120
                new Item("B", -12.0), // -20, -10
                new Item("C", -10.0), // -10, 0
                new Item("D", -0.1),  // -10, 0
                new Item("E", 0.0),   //   0, 10
                new Item("F", 0.1),   //   0, 10
                new Item("G", 9.9),   //   0, 10
                new Item("H", 10.0),  //  10, 20
                new Item("I", 10.1),  //  10, 20
                new Item("J", 19.9),  //  10, 20
                new Item("K", 20.0),  //  20, 30
                new Item("L", 20.1),  //  20, 30
                new Item("M", 25.5),  //  20, 30
                new Item("N", 29.99), //  20, 30
                new Item("O", 120.1)  //  120, 130
        );

        Function<Item, Double> extractor = i -> i.value;
        double intervalo = 10.0;
        List<Estaca<Item>> estacas = EstaqueamentoUtils.realizaEstaqueamento(intervalo, items, extractor);

        assertEquals(7, estacas.size());

        assertInterval(estacas.get(0), -130.0, -120.0, "A");
        assertInterval(estacas.get(1), -20.0, -10.0, "B");
        assertInterval(estacas.get(2), -10.0, 0.0, "C", "D");
        assertInterval(estacas.get(3), 0.0, 10.0, "E", "F", "G");
        assertInterval(estacas.get(4), 10.0, 20.0, "H", "I", "J");
        assertInterval(estacas.get(5), 20.0, 30.0, "K", "L", "M", "N");
        assertInterval(estacas.get(6), 120.0, 130.0, "O");
    }

    @Test
    @DisplayName("Builds contiguous intervals with interval=1000 and buckets items correctly")
    void bucketsItemsCorrectlyWithInterval1000() {
        List<Item> items = List.of(
                new Item("A", -2500.5), // -3000, -2000
                new Item("B", -1200.0), // -2000, -1000
                new Item("C", -1000.0), // -1000, 0
                new Item("D", -500.7),  // -1000, 0
                new Item("E", -0.1),    // -1000, 0
                new Item("F", 0.0),     //     0, 1000
                new Item("G", 0.5),     //     0, 1000
                new Item("H", 500.0),   //     0, 1000
                new Item("I", 999.9),   //     0, 1000
                new Item("J", 1000.0),  //  1000, 2000
                new Item("K", 1200.3),  //  1000, 2000
                new Item("L", 1999.9),  //  1000, 2000
                new Item("M", 2000.0),  //  2000, 3000
                new Item("N", 2500.8),  //  2000, 3000
                new Item("O", 5200.1),  //  5000, 6000
                new Item("P", 12500.0), // 12000, 13000
                new Item("Q", 15800.7)  // 15000, 16000
        );

        Function<Item, Double> extractor = i -> i.value;
        double intervalo = 1000.0;
        List<Estaca<Item>> estacas = EstaqueamentoUtils.realizaEstaqueamento(intervalo, items, extractor);

        assertEquals(9, estacas.size());

        assertInterval(estacas.get(0), -3000.0, -2000.0, "A");
        assertInterval(estacas.get(1), -2000.0, -1000.0, "B");
        assertInterval(estacas.get(2), -1000.0, 0.0, "C", "D", "E");
        assertInterval(estacas.get(3), 0.0, 1000.0, "F", "G", "H", "I");
        assertInterval(estacas.get(4), 1000.0, 2000.0, "J", "K", "L");
        assertInterval(estacas.get(5), 2000.0, 3000.0, "M", "N");
        assertInterval(estacas.get(6), 5000.0, 6000.0, "O");
        assertInterval(estacas.get(7), 12000.0, 13000.0, "P");
        assertInterval(estacas.get(8), 15000.0, 16000.0, "Q");
    }

    @Test
    @DisplayName("Builds contiguous intervals with interval=10000 and buckets items correctly")
    void bucketsItemsCorrectlyWithInterval10000() {
        List<Item> items = List.of(
                new Item("A", -25000.8), // -30000, -20000
                new Item("B", -15000.0), // -20000, -10000
                new Item("C", -10000.0), // -10000, 0
                new Item("D", -5000.3),  // -10000, 0
                new Item("E", -0.1),     // -10000, 0
                new Item("F", 0.0),      //      0, 10000
                new Item("G", 2500.7),   //      0, 10000
                new Item("H", 9999.9),   //      0, 10000
                new Item("I", 10000.0),  //  10000, 20000
                new Item("J", 15000.5),  //  10000, 20000
                new Item("K", 19999.9),  //  10000, 20000
                new Item("L", 20000.0),  //  20000, 30000
                new Item("M", 25000.2),  //  20000, 30000
                new Item("N", 29999.9),  //  20000, 30000
                new Item("O", 45000.1),  //  40000, 50000
                new Item("P", 75000.0),  //  70000, 80000
                new Item("Q", 125000.5), // 120000, 130000
                new Item("R", 200000.0)  // 200000, 210000
        );

        Function<Item, Double> extractor = i -> i.value;
        double intervalo = 10000.0;
        List<Estaca<Item>> estacas = EstaqueamentoUtils.realizaEstaqueamento(intervalo, items, extractor);

        assertEquals(10, estacas.size());

        assertInterval(estacas.get(0), -30000.0, -20000.0, "A");
        assertInterval(estacas.get(1), -20000.0, -10000.0, "B");
        assertInterval(estacas.get(2), -10000.0, 0.0, "C", "D", "E");
        assertInterval(estacas.get(3), 0.0, 10000.0, "F", "G", "H");
        assertInterval(estacas.get(4), 10000.0, 20000.0, "I", "J", "K");
        assertInterval(estacas.get(5), 20000.0, 30000.0, "L", "M", "N");
        assertInterval(estacas.get(6), 40000.0, 50000.0, "O");
        assertInterval(estacas.get(7), 70000.0, 80000.0, "P");
        assertInterval(estacas.get(7), 70000.0, 80000.0, "P");
        assertInterval(estacas.get(8), 120000.0, 130000.0, "Q");
        assertInterval(estacas.get(9), 200000.0, 210000.0, "R");
    }

    @Test
    @DisplayName("Builds contiguous intervals with interval=0.1 and buckets items correctly")
    void bucketsItemsCorrectlyWithInterval01() {
        List<Item> items = List.of(
                new Item("A", -1.25),   // -1.3, -1.2
                new Item("B", -0.15),   // -0.2, -0.1
                new Item("C", -0.1),    // -0.1, 0.0
                new Item("D", -0.05),   // -0.1, 0.0
                new Item("E", 0.0),     //  0.0, 0.1
                new Item("F", 0.05),    //  0.0, 0.1
                new Item("G", 0.09),    //  0.0, 0.1
                new Item("H", 0.1),     //  0.1, 0.2
                new Item("I", 0.15),    //  0.1, 0.2
                new Item("J", 0.19),    //  0.1, 0.2
                new Item("K", 0.2),     //  0.2, 0.3
                new Item("L", 0.25),    //  0.2, 0.3
                new Item("M", 0.29),    //  0.2, 0.3
                new Item("N", 0.3),     //  0.3, 0.4
                new Item("O", 0.35),    //  0.3, 0.4
                new Item("P", 0.88),    //  0.8, 0.9
                new Item("Q", 1.25),    //  1.2, 1.3
                new Item("R", 2.47),    //  2.4, 2.5
                new Item("S", 5.83)     //  5.8, 5.9
        );

        Function<Item, Double> extractor = i -> i.value;
        double intervalo = 0.1;
        List<Estaca<Item>> estacas = EstaqueamentoUtils.realizaEstaqueamento(intervalo, items, extractor);

        assertEquals(11, estacas.size());

        assertInterval(estacas.get(0), -1.3, -1.2, "A");
        assertInterval(estacas.get(1), -0.2, -0.1, "B");
        assertInterval(estacas.get(2), -0.1, 0.0, "C", "D");
        assertInterval(estacas.get(3), 0.0, 0.1, "E", "F", "G");
        assertInterval(estacas.get(4), 0.1, 0.2, "H", "I", "J");
        assertInterval(estacas.get(5), 0.2, 0.3, "K", "L", "M");
        assertInterval(estacas.get(6), 0.3, 0.4, "N", "O");
        assertInterval(estacas.get(7), 0.8, 0.9, "P");
        assertInterval(estacas.get(8), 1.2, 1.3, "Q");
        assertInterval(estacas.get(9), 2.4, 2.5, "R");
        assertInterval(estacas.get(10), 5.8, 5.9, "S");
    }

    @Test
    @DisplayName("Builds contiguous intervals with interval=0.01 and buckets items correctly")
    void bucketsItemsCorrectlyWithInterval001() {
        List<Item> items = List.of(
                new Item("A", -0.125),  // -0.13, -0.12
                new Item("B", -0.015),  // -0.02, -0.01
                new Item("C", -0.01),   // -0.01, 0.00
                new Item("D", -0.005),  // -0.01, 0.00
                new Item("E", 0.0),     //  0.00, 0.01
                new Item("F", 0.005),   //  0.00, 0.01
                new Item("G", 0.009),   //  0.00, 0.01
                new Item("H", 0.01),    //  0.01, 0.02
                new Item("I", 0.015),   //  0.01, 0.02
                new Item("J", 0.019),   //  0.01, 0.02
                new Item("K", 0.02),    //  0.02, 0.03
                new Item("L", 0.025),   //  0.02, 0.03
                new Item("M", 0.029),   //  0.02, 0.03
                new Item("N", 0.03),    //  0.03, 0.04
                new Item("O", 0.035),   //  0.03, 0.04
                new Item("P", 0.088),   //  0.08, 0.09
                new Item("Q", 0.125),   //  0.12, 0.13
                new Item("R", 0.247),   //  0.24, 0.25
                new Item("S", 0.583),   //  0.58, 0.59
                new Item("T", 1.234),   //  1.23, 1.24
                new Item("U", 2.876)    //  2.87, 2.88
        );

        Function<Item, Double> extractor = i -> i.value;
        double intervalo = 0.01;
        List<Estaca<Item>> estacas = EstaqueamentoUtils.realizaEstaqueamento(intervalo, items, extractor);

        assertEquals(13, estacas.size());

        assertInterval(estacas.get(0), -0.13, -0.12, "A");
        assertInterval(estacas.get(1), -0.02, -0.01, "B");
        assertInterval(estacas.get(2), -0.01, 0.00, "C", "D");
        assertInterval(estacas.get(3), 0.00, 0.01, "E", "F", "G");
        assertInterval(estacas.get(4), 0.01, 0.02, "H", "I", "J");
        assertInterval(estacas.get(5), 0.02, 0.03, "K", "L", "M");
        assertInterval(estacas.get(6), 0.03, 0.04, "N", "O");
        assertInterval(estacas.get(7), 0.08, 0.09, "P");
        assertInterval(estacas.get(8), 0.12, 0.13, "Q");
        assertInterval(estacas.get(9), 0.24, 0.25, "R");
        assertInterval(estacas.get(10), 0.58, 0.59, "S");
        assertInterval(estacas.get(11), 1.23, 1.24, "T");
        assertInterval(estacas.get(12), 2.87, 2.88, "U");
    }

    @Test
    @DisplayName("Works with fractional intervals and creates helper to fetch T values")
    void fractionalIntervalsWithInnerHelper() {
        // Inner test class acting as value carrier
        class ValueHolder<T> {
            final T value;

            ValueHolder(T v) {
                this.value = v;
            }
        }

        // Helper to easily get back the list of T values per Estaca
        class EstacaValues<T> {
            final Estaca<ValueHolder<T>> estaca;

            EstacaValues(Estaca<ValueHolder<T>> e) {
                this.estaca = e;
            }

            List<T> values() {
                return estaca.items().stream().map(v -> v.value).collect(Collectors.toList());
            }
        }

        List<ValueHolder<Double>> items = List.of(new ValueHolder<>(2.1), new ValueHolder<>(2.6), new ValueHolder<>(2.5));
        List<Estaca<ValueHolder<Double>>> estacas = EstaqueamentoUtils.realizaEstaqueamento(0.5, items, v -> v.value);

        // Intervals: from current(min=2.1)=2.0 up to next(max=2.6)=3.0 -> [2.0,2.5),[2.5,3.0)
        assertEquals(2, estacas.size());

        EstacaValues<Double> first = new EstacaValues<>(estacas.get(0));
        EstacaValues<Double> second = new EstacaValues<>(estacas.get(1));

        assertEquals(List.of(2.1), first.values());
        // Note: boundary item 2.5 should be in second interval [2.5,3.0)
        assertEquals(List.of(2.5, 2.6), second.values());
    }


    // Helper assertion to verify an Estaca interval and its item ids in order
    private static void assertInterval(
            final Estaca<Item> estaca,
            final double expectedInicio,
            final double expectedFim,
            String... expectedItemIds
    ) {

        assertEquals(expectedInicio, estaca.inicio(), 1e-9, "inicio");
        assertEquals(expectedFim, estaca.fim(), 1e-9, "fim");

        List<String> ids = estaca.items().stream().map(Item::id).sorted().toList();
        assertEquals(List.of(expectedItemIds), ids);
    }


    // Items across negative and positive ranges with various boundaries
    private record Item(String id, double value) {}
}
