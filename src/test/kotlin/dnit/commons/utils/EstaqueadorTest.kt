package dnit.commons.utils

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Estaqueador Tests")
class EstaqueadorTest {

    private lateinit var estaqueador : Estaqueador<String>

    @BeforeEach
    fun setUp() {
        estaqueador = Estaqueador()
    }

    @Nested
    @DisplayName("Insert Method Tests")
    inner class InsertTests {

        @Test
        @DisplayName("Should insert single item successfully")
        fun shouldInsertSingleItem() {
            estaqueador.insert(10.0, "Item1")

            val result = estaqueador.retrieveClosestItem(10.0)
            assertEquals("Item1", result)
        }


        @Test
        @DisplayName("Should insert multiple items successfully")
        fun shouldInsertMultipleItems() {
            estaqueador.insert(10.0, "Item1")
            estaqueador.insert(20.0, "Item2")
            estaqueador.insert(30.0, "Item3")

            assertEquals("Item1", estaqueador.retrieveClosestItem(0.0))
            assertEquals("Item1", estaqueador.retrieveClosestItem(10.0))
            assertEquals("Item1", estaqueador.retrieveClosestItem(14.0))
            assertEquals("Item2", estaqueador.retrieveClosestItem(16.0))
            assertEquals("Item2", estaqueador.retrieveClosestItem(20.0))
            assertEquals("Item2", estaqueador.retrieveClosestItem(24.0))
            assertEquals("Item3", estaqueador.retrieveClosestItem(36.0))
            assertEquals("Item3", estaqueador.retrieveClosestItem(30.0))
            assertEquals("Item3", estaqueador.retrieveClosestItem(999.0))
        }

        @Test
        @DisplayName("Should auto-increment key when key already exists")
        fun shouldAutoIncrementKeyWhenKeyExists() {
            estaqueador.insert(10.0, "First")
            estaqueador.insert(10.0, "Second") // Should use 10.00001
            estaqueador.insert(10.0, "Third")  // Should use 10.00002


            // Verify all values are accessible
            assertEquals("First", estaqueador.retrieveClosestItem(10.0))
            assertEquals("Second", estaqueador.retrieveClosestItem(10.00001))
            assertEquals("Third", estaqueador.retrieveClosestItem(10.00002))
        }

        @Test
        @DisplayName("Should handle negative keys")
        fun shouldHandleNegativeKeys() {
            estaqueador.insert(-10.0, "NegativeItem")
            estaqueador.insert(0.0, "ZeroItem")
            estaqueador.insert(10.0, "PositiveItem")

            assertEquals("NegativeItem", estaqueador.retrieveClosestItem(-10.0))
            assertEquals("NegativeItem", estaqueador.retrieveClosestItem(-6.0))
            assertEquals("ZeroItem", estaqueador.retrieveClosestItem(0.0))
            assertEquals("PositiveItem", estaqueador.retrieveClosestItem(6.0))
            assertEquals("PositiveItem", estaqueador.retrieveClosestItem(10.0))
        }
    }


    @Nested
    @DisplayName("Retrieve Closest Item Tests")
    inner class RetrieveClosestItemTests {

        @Test
        @DisplayName("Should return exact match when key exists")
        fun shouldReturnExactMatch() {
            setupMultipleItems()

            assertEquals("Item20", estaqueador.retrieveClosestItem(20.0))
            assertEquals("Item50", estaqueador.retrieveClosestItem(50.0))
        }

        @Test
        @DisplayName("Should return closest item when key doesn't exist")
        fun shouldReturnClosestItem() {
            setupMultipleItems()

            // Closer to 10.0
            assertEquals("Item10", estaqueador.retrieveClosestItem(12.0))

            // Closer to 30.0
            assertEquals("Item30", estaqueador.retrieveClosestItem(28.0))

            // Equidistant - should prefer lower key
            assertEquals("Item20", estaqueador.retrieveClosestItem(25.0))
        }

        @Test
        @DisplayName("Should handle queries outside range")
        fun shouldHandleQueriesOutsideRange() {
            setupMultipleItems()

            // Below minimum
            assertEquals("Item0", estaqueador.retrieveClosestItem(-5.0))

            // Above maximum
            assertEquals("Item100", estaqueador.retrieveClosestItem(6000.0))
        }

        @Test
        @DisplayName("Should prefer lower key on equal distances")
        fun shouldPreferLowerKeyOnEqualDistances() {
            estaqueador.insert(10.0, "Lower")
            estaqueador.insert(20.0, "Higher")

            // Exactly in the middle - should prefer lower
            assertEquals("Lower", estaqueador.retrieveClosestItem(15.0))
        }

        @Test
        @DisplayName("Should handle single item")
        fun shouldHandleSingleItem() {
            estaqueador.insert(10.0, "OnlyItem")

            assertEquals("OnlyItem", estaqueador.retrieveClosestItem(5.0))
            assertEquals("OnlyItem", estaqueador.retrieveClosestItem(10.0))
            assertEquals("OnlyItem", estaqueador.retrieveClosestItem(15.0))
        }
    }

    @Nested
    @DisplayName("Retrieve In Range Tests")
    inner class RetrieveInRangeTests {

        @Test
        @DisplayName("Should return items within range (inclusive)")
        fun shouldReturnItemsWithinRange() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(20.0, 40.0)
            assertEquals(3, result.size)
            assertTrue(result.containsAll(listOf("Item20", "Item30", "Item40")))
        }

        @Test
        @DisplayName("Should handle reversed range parameters")
        fun shouldHandleReversedRangeParameters() {
            setupMultipleItems()

            val result1 = estaqueador.retrieveInRange(20.0, 40.0)
            val result2 = estaqueador.retrieveInRange(40.0, 20.0)

            assertEquals(result1, result2)
        }

        @Test
        @DisplayName("Should return empty list when no items in range")
        fun shouldReturnEmptyListWhenNoItemsInRange() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(51.0, 59.0)
            assertTrue(result.isEmpty())
        }

        @Test
        @DisplayName("Should include boundary values")
        fun shouldIncludeBoundaryValues() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(10.0, 50.0)
            assertEquals(5, result.size)
            assertTrue(result.containsAll(listOf("Item10", "Item20", "Item30", "Item40", "Item50")))
        }

        @Test
        @DisplayName("Should handle single point range")
        fun shouldHandleSinglePointRange() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(30.0, 30.0)
            assertEquals(1, result.size)
            assertEquals("Item30", result[0])
        }

        @Test
        @DisplayName("Should handle range with fractional boundaries")
        fun shouldHandleRangeWithFractionalBoundaries() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(19.5, 30.5)
            assertEquals(2, result.size)
            assertTrue(result.containsAll(listOf("Item20", "Item30")))
        }
    }

    @Nested
    @DisplayName("Retrieve In Range Or Closest Tests")
    inner class RetrieveInRangeOrClosestTests {

        @Test
        @DisplayName("Should return items in range when available")
        fun shouldReturnItemsInRangeWhenAvailable() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRangeOrClosest(20.0, 40.0)
            assertEquals(3, result.size)
            assertTrue(result.containsAll(listOf("Item20", "Item30", "Item40")))
        }

        @Test
        @DisplayName("Should return closest item when no items in range")
        fun shouldReturnClosestItemWhenNoItemsInRangeLowerBound() {
            setupMultipleItems()

            // Range between 41-42 has no items, closest to average (41.5) is Item40
            val result = estaqueador.retrieveInRangeOrClosest(41.0, 42.0)
            assertEquals(1, result.size)
            assertEquals("Item40", result[0])
        }

        @Test
        @DisplayName("Should return closest item when no items in range")
        fun shouldReturnClosestItemWhenNoItemsInRangeHigherBound() {
            setupMultipleItems()

            // Range between 45-48 has no items, closest to average (46.5) is Item50
            val result = estaqueador.retrieveInRangeOrClosest(45.0, 48.0)
            assertEquals(1, result.size)
            assertEquals("Item50", result[0])
        }

        @Test
        @DisplayName("Should return single item when closest entries are the same")
        fun shouldReturnSingleItemWhenClosestEntriesAreSame() {
            estaqueador.insert(25.0, "MiddleItem")

            val result = estaqueador.retrieveInRangeOrClosest(20.0, 30.0)
            assertEquals(1, result.size)
            assertEquals("MiddleItem", result[0])
        }

        @Test
        @DisplayName("Should handle value out of range")
        fun shouldHandleValueOutOfRange() {
            setupMultipleItems()
            val result = estaqueador.retrieveInRangeOrClosest(11.0, 19.0)
            assertEquals(1, result.size)
        }

        @Test
        @DisplayName("Should prefer item closer to average when equidistant")
        fun shouldPreferItemCloserToAverageWhenEquidistant() {
            estaqueador.insert(10.0, "Item10")
            estaqueador.insert(30.0, "Item30")

            // Range 15-25, average is 20, both items are equidistant from 20
            // Should prefer Item10 (distance 10) over Item30 (distance 10) due to <= comparison
            val result = estaqueador.retrieveInRangeOrClosest(15.0, 25.0)
            assertEquals(1, result.size)
            assertEquals("Item10", result[0])
        }

        @Test
        @DisplayName("Should handle asymmetric closest items")
        fun shouldHandleAsymmetricClosestItems() {
            estaqueador.insert(10.0, "Item10")
            estaqueador.insert(40.0, "Item40")

            // Range 20-25, average is 22.5
            // Item10 distance to 22.5 = 12.5
            // Item40 distance to 22.5 = 17.5
            // Should prefer Item10
            val result = estaqueador.retrieveInRangeOrClosest(20.0, 25.0)
            assertEquals(1, result.size)
            assertEquals("Item10", result[0])
        }

        @Test
        @DisplayName("Should handle asymmetric closest items")
        fun shouldHandleAsymmetricClosestItemsCloserToMax() {
            estaqueador.insert(10.0, "Item10")
            estaqueador.insert(40.0, "Item40")

            // Range 20-25, average is 22.5
            // Item10 distance to 22.5 = 12.5
            // Item40 distance to 22.5 = 17.5
            // Should prefer Item10
            val result = estaqueador.retrieveInRangeOrClosest(30.0, 35.0)
            assertEquals(1, result.size)
            assertEquals("Item40", result[0])
        }
    }

    @Nested
    @DisplayName("Complex Scenarios Tests")
    inner class ComplexScenariosTests {

        @Test
        @DisplayName("Should handle large dataset efficiently")
        fun shouldHandleLargeDatasetEfficiently() {
            // Insert 1_000 items
            repeat(1_000) { i ->
                estaqueador.insert(i.toDouble(), "Item$i")
            }

            // Test various operations
            assertEquals("Item500", estaqueador.retrieveClosestItem(500.0))

            val rangeResult = estaqueador.retrieveInRange(100.0, 110.0)
            assertEquals(11, rangeResult.size) // 100 to 110 inclusive

            val closestResult = estaqueador.retrieveInRangeOrClosest(1500.0, 1600.0)
            assertEquals(1, closestResult.size)
            assertEquals("Item999", closestResult[0]) // Closest to range outside dataset
        }

        @Test
        @DisplayName("Should handle decimal precision correctly")
        fun shouldHandleDecimalPrecisionCorrectly() {
            estaqueador.insert(10.1, "Item10_1")
            estaqueador.insert(10.15, "Item10_15")
            estaqueador.insert(10.2, "Item10_2")

            assertEquals("Item10_15", estaqueador.retrieveClosestItem(10.14))
            assertEquals("Item10_15", estaqueador.retrieveClosestItem(10.16))

            val rangeResult = estaqueador.retrieveInRange(10.12, 10.18)
            assertEquals(1, rangeResult.size)
            assertEquals("Item10_15", rangeResult[0])
        }

        @Test
        @DisplayName("Should maintain order in returned lists")
        fun shouldMaintainOrderInReturnedLists() {
            setupMultipleItems()

            val result = estaqueador.retrieveInRange(10.0, 50.0)

            // Verify that items are returned in key order
            val expected = listOf("Item10", "Item20", "Item30", "Item40", "Item50")
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Should work with different data types")
        fun shouldWorkWithDifferentDataTypes() {
            val intEstaqueamento = Estaqueador<Int>()

            intEstaqueamento.insert(10.0, 100)
            intEstaqueamento.insert(20.0, 200)
            intEstaqueamento.insert(30.0, 300)

            assertEquals(200, intEstaqueamento.retrieveClosestItem(25.0))

            val rangeResult = intEstaqueamento.retrieveInRange(15.0, 25.0)
            assertEquals(1, rangeResult.size)
            assertEquals(200, rangeResult[0])
        }
    }


    /**
     * Helper method to set up multiple items for testing
     */
    private fun setupMultipleItems() {
        estaqueador.insert(0.000, "Item0")
        estaqueador.insert(10.00, "Item10")
        estaqueador.insert(20.00, "Item20")
        estaqueador.insert(30.00, "Item30")
        estaqueador.insert(40.00, "Item40")
        estaqueador.insert(50.00, "Item50")
        estaqueador.insert(60.00, "Item60")
        estaqueador.insert(70.00, "Item70")
        estaqueador.insert(80.00, "Item80")
        estaqueador.insert(90.00, "Item90")
        estaqueador.insert(100.0, "Item100")
    }
}