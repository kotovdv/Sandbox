package com.kotovdv.sandbox.multithreading.first.collection

import java.util.Arrays.copyOf

/**
 * Set of integers that keep elements in a sorted order
 *
 * Operations of insertions take O(n) time in worst case scenario, meanwhile position lookup takes O(log N)
 *
 * The same thing could be implemented using the Binary Heap data structure, which would be more effective
 */
class BinarySortedIntSet(capacity: Int = 10) : SortedIntSet {

    private var storage: Array<Int?> = arrayOfNulls(capacity)
    private var size: Int = 0

    override fun put(value: Int) {
        checkStorageCapacity()
        val position = findPosition(value, 0, size - 1)
        if (position != null) {
            insertValue(position, value)
        }
    }

    private fun insertValue(position: Int, value: Int) {
        storage.insertAt(position, value)
        size++
    }

    override fun toArray(): Array<Int> {
        return copyOf(storage, size)
    }

    private fun checkStorageCapacity() {
        if (size + 1 == storage.size) {
            doubleStorageSize()
        }
    }

    private fun doubleStorageSize() {
        val newStorage = arrayOfNulls<Int>(storage.size * 2)
        System.arraycopy(storage, 0, newStorage, 0, storage.size)
        storage = newStorage
    }

    private fun findPosition(value: Int, startPosition: Int, endPosition: Int): Int? {
        if (endPosition - startPosition < 0) {
            return startPosition
        }

        val center = startPosition + (endPosition - startPosition) / 2
        val centerValue = storage[center]!!

        if (centerValue == value) {
            return null
        } else if (centerValue > value) {
            return findPosition(value, startPosition, center - 1)
        } else {
            return findPosition(value, center + 1, endPosition)
        }
    }
}

private fun Array<Int?>.insertAt(position: Int, value: Int) {
    System.arraycopy(this, position, this, position + 1, size - position - 1)
    this[position] = value
}