package dnit.commons.snv.impl

import dnit.commons.model.internal.MiniTrechoSNV
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

class CacheMiniTrechos(private val maxItems : Int = 10) {

    private val cache = ConcurrentHashMap<String, List<MiniTrechoSNV>>()
    private val insertionOrder = ConcurrentLinkedQueue<String>()



    internal suspend fun obtemCacheOrFetch(
        uf : String,
        br : String,
        tipo : String,
        data : String,
        fetcher: suspend () -> List<MiniTrechoSNV>
    ) : List<MiniTrechoSNV> {
        val cacheKey = key(uf, br, tipo, data)

        return cache[cacheKey] ?: run {
            val fetchedData = fetcher()
            add(uf, br, tipo, data, fetchedData)
            fetchedData
        }
    }



    internal fun add(
        uf : String,
        br : String,
        tipo : String,
        data : String,
        miniTrechos : List<MiniTrechoSNV>,
    ) {
        val cacheKey = key(uf, br, tipo, data)

        // If key already exists, don't add to queue again
        if (!cache.containsKey(cacheKey)) {
            // If cache is full, remove the oldest item (first in queue)
            if (cache.size >= maxItems) {
                val oldestKey = insertionOrder.poll()
                oldestKey?.let { cache.remove(it) }
            }

            // Add new key to insertion order queue
            insertionOrder.offer(cacheKey)
        }

        // Add/update the item in cache
        cache[cacheKey] = miniTrechos
    }



    private fun key(uf : String, br : String, tipo : String, data : String) : String {
        return "$uf-$br-$tipo-$data".uppercase()
    }

}