package dnit.commons.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CollectionUtilsTest {

    @Nested
    class IsNullOrEmpty {

        @Test
        void shouldReturnTrueWhenCollectionIsNull() {
            List list = null;
            assertTrue(CollectionUtils.isNullOrEmpty(list));
            assertFalse(CollectionUtils.hasItems(list));
        }


        @Test
        void shouldReturnTrueWhenCollectionIsEmpty() {
            assertTrue(CollectionUtils.isNullOrEmpty(Collections.emptyList()));
            assertFalse(CollectionUtils.hasItems(Collections.emptyList()));
        }


        @Test
        void shouldReturnFalseWhenCollectionIsNotEmpty() {
            List<String> list = Arrays.asList("item");
            assertFalse(CollectionUtils.isNullOrEmpty(list));
            assertTrue(CollectionUtils.hasItems(list));
        }
    }

    @Nested
    class IsNullOrEmptyMap {

        @Test
        void shouldReturnTrueWhenCollectionIsNull() {
            Map map = null;
            assertTrue(CollectionUtils.isNullOrEmpty(map));
            assertFalse(CollectionUtils.hasItems(map));
        }


        @Test
        void shouldReturnTrueWhenCollectionIsEmpty() {
            assertTrue(CollectionUtils.isNullOrEmpty(new HashMap<>()));
            assertFalse(CollectionUtils.hasItems(new HashMap<>()));
        }


        @Test
        void shouldReturnFalseWhenCollectionIsNotEmpty() {
            Map<String, String> map = Collections.singletonMap("key", "value");
            assertFalse(CollectionUtils.isNullOrEmpty(map));
            assertTrue(CollectionUtils.hasItems(map));
        }
    }


    @Nested
    class ToSet {

        @Test
        void shouldReturnSetWithAllItems() {
            Set<String> result = CollectionUtils.toSet("A", "B", "C");
            assertEquals(3, result.size());
            assertTrue(result.containsAll(Arrays.asList("A", "B", "C")));
        }


        @Test
        void shouldReturnEmptySetWhenNoItemsProvided() {
            Set<String> result = CollectionUtils.toSet();
            assertTrue(result.isEmpty());
        }


        @Test
        void shouldHandleDuplicateItems() {
            Set<String> result = CollectionUtils.toSet("A", "A", "B");
            assertEquals(2, result.size());
            assertTrue(result.containsAll(Arrays.asList("A", "B")));
        }
    }


    @Nested
    class SafeList {

        @Test
        void shouldReturnEmptyListWhenInputIsNull() {
            List<String> result = CollectionUtils.safeList(null);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }


        @Test
        void shouldReturnSameListWhenInputIsNotNull() {
            List<String> original = Arrays.asList("X", "Y");
            List<String> result = CollectionUtils.safeList(original);
            assertSame(original, result);
        }
    }

}