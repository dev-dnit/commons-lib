package dnit.commons.utils

import java.util.TreeMap
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


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
class EstaqueamentoUtils<T> {


    /**
     * Mapa ordenado que mantém os pares chave-valor organizados automaticamente
     * pela chave Double, permitindo operações eficientes de busca e intervalos.
     */
    private val map = TreeMap<Double, T>()



    /**
     * Insere um novo par chave-valor no estaqueamento.
     */
    fun insert(key: Double, value: T) {
        var actualKey = key
        val increment = 0.00001
        val maxAttempts = 500 // Prevent infinite loops
        var attempts = 0

        while (map.containsKey(actualKey) && attempts < maxAttempts) {
            actualKey += increment
            attempts++
        }

        if (attempts >= maxAttempts) {
            return
        }

        map[actualKey] = value
    }



    /**
     * Recupera o item mais próximo à chave especificada.
     * Em caso de empate na distância, prioriza o valor com chave menor.
     */
    fun retrieveClosestItem(key: Double): T {
        require(map.isNotEmpty()) { "Nenhum valor definido para o estaqueamento" }

        val lower = map.floorEntry(key)
        val higher = map.ceilingEntry(key)

        return when {
            lower == null && higher != null -> higher.value
            lower != null && higher == null -> lower.value
            else -> {
                val lowerDistance = abs(key - lower.key)
                val higherDistance = abs(key - higher.key)
                if (lowerDistance <= higherDistance) lower.value else higher.value
            }
        }
    }



    /**
     * Recupera todos os itens dentro do intervalo especificado (inclusive).
     * A ordem dos parâmetros startKey e endKey não importa - o método
     * automaticamente determina o menor e maior valor.
     */
    fun retrieveInRange(startKey: Double, endKey: Double): List<T> {
        require(map.isNotEmpty()) { "Nenhum valor definido para o estaqueamento" }

        val minKey = min(startKey, endKey)
        val maxKey = max(startKey, endKey)

        return map.subMap(minKey, true, maxKey, true).values.toList()
    }



    /**
     * Recupera itens no intervalo especificado ou, se não houver itens no intervalo,
     * retorna um único item baseado no valor mais próximo
     */
    fun retrieveInRangeOrClosest(startKey: Double, endKey: Double): List<T> {
        val inRange = retrieveInRange(startKey, endKey)

        return inRange.ifEmpty {
            // Busca os itens mais próximos aos extremos
            val start = findClosestEntry(startKey)
            val end = findClosestEntry(endKey)

            when {
                start == null && end == null -> emptyList()
                start == null && end != null -> listOf(end.value)
                start != null && end == null -> listOf(start.value)
                start != null && end != null && start.key == end.key -> listOf(start.value)
                else -> {
                    // Calcula a média das chaves e retorna o item mais próximo à média
                    val avgKey = abs(startKey + endKey) / 2.0
                    val distanceToStart = abs(avgKey - start!!.key)
                    val distanceToEnd = abs(avgKey - end!!.key)

                    val closestToAverage = if (distanceToStart <= distanceToEnd) {
                        start.value
                    } else {
                        end.value
                    }

                    listOf(closestToAverage)
                }
            }
        }
    }



    private fun findClosestEntry(key: Double): Map.Entry<Double, T>? {
        if (map.isEmpty()) return null

        val lower = map.floorEntry(key)
        val higher = map.ceilingEntry(key)

        return when {
            lower == null -> higher
            higher == null -> lower
            else -> {
                val lowerDistance = abs(key - lower.key)
                val higherDistance = abs(key - higher.key)
                if (lowerDistance <= higherDistance) lower else higher
            }
        }
    }


}