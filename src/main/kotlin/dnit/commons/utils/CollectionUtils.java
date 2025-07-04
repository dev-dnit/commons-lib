package dnit.commons.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Classe utilitária para manipulação de coleções.
 */
public final class CollectionUtils {

    private CollectionUtils() { }



    /**
     * Verifica se a coleção possui ao menos um item
     */
    public static boolean hasItems(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }



    /**
     * Verifica se a coleção possui ao menos um item
     */
    public static boolean hasItems(Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }



    /**
     * Verifica se a coleção é nula ou vazia
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }



    /**
     * Verifica se a coleção é nula ou vazia
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }



    /**
     * Transforma um array de itens em um Set.
     */
    @SafeVarargs
    public static <T> Set<T> toSet(T... items) {
        if (items == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(Arrays.asList(items));
    }



    /**
     * Traduz uma coleção para uma lista, retornando uma lista vazia se a coleção for nula.
     */
    public static <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

}
