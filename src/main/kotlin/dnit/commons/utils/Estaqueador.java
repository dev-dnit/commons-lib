package dnit.commons.utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Classe utilitária para gerenciamento de valores associados a estaqueamentos (pontos quilométricos)
 * utilizando Double como chave de indexação.
 *
 * Esta classe oferece operações eficientes para inserção, busca por proximidade e recuperação
 * de intervalos de valores, utilizando internamente uma TreeMap para garantir ordenação
 * automática e operações de busca em O(log n).
 *
 * @param T Tipo do valor a ser armazenado associado à chave Double
 */
public class Estaqueador<T> {


    public static final double EPSILON = 0.00001;


    /**
     * Mapa ordenado que mantém os pares chave-valor organizados automaticamente
     * pela chave Double, permitindo operações eficientes de busca e intervalos.
     */
    private TreeMap<Double, T> map = new TreeMap<>();



    /**
     * Insere um novo par chave-valor no estaqueamento.
     */
    public void insert(Double key, T value) {
        var actualKey = key;
        final var increment = EPSILON;
        final var maxAttempts = 500; // Prevent infinite loops
        var attempts = 0;

        while (map.containsKey(actualKey) && attempts < maxAttempts) {
            actualKey += increment;
            attempts++;
        }

        if (attempts >= maxAttempts) {
            return;
        }

        map.put(actualKey, value);
    }



    /**
     * Recupera o item mais próximo à chave especificada.
     * Em caso de empate na distância, prioriza o valor com chave menor.
     */
    public T retrieveClosestItem(Double key) {
        final var lower = map.floorEntry(key);
        final var higher = map.ceilingEntry(key);

        if (lower == null && higher != null) return higher.getValue();
        if (lower != null && higher == null) return lower.getValue();

        final var lowerDistance = Math.abs(key - lower.getKey());
        final var higherDistance = Math.abs(key - higher.getKey());

        return lowerDistance <= higherDistance ? lower.getValue() : higher.getValue();
    }



    /**
     * Recupera todos os itens dentro do intervalo especificado (inclusive).
     * A ordem dos parâmetros startKey e endKey não importa - o método
     * automaticamente determina o menor e maior valor.
     */
    public List<T> retrieveInRange(Double startKey, Double endKey) {
        final var minKey = Math.min(startKey, endKey);
        final var maxKey = Math.max(startKey, endKey);

        return new ArrayList<>(map.subMap(minKey, true, maxKey, true).values());
    }



    /**
     * Recupera itens no intervalo especificado ou, se não houver itens no intervalo,
     * retorna um único item baseado no valor mais próximo
     */
    public List<T> retrieveInRangeOrClosest(double startKey, double endKey) {
        List<T> inRange = retrieveInRange(startKey, endKey);

        if (!inRange.isEmpty()) {
            return inRange;

        } else {
            var average = (startKey + endKey) / 2;
            return Collections.singletonList(retrieveClosestItem(average));
        }
    }


}