package com.kotovdv.sandbox.multithreading.first.collection

/**
 * Thread safe decorator for SortedIntSet implementations
 */
class SynchronizedSortedIntSet(private val sortedIntSet: SortedIntSet) : SortedIntSet {
    override fun put(value: Int) {
        synchronized(this, block = { sortedIntSet.put(value) })
    }

    override fun toArray(): Array<Int> {
        synchronized(this, block = { return sortedIntSet.toArray() })
    }
}