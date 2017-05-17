package com.kotovdv.sandbox.multithreading.first.collection

/**
 * Stores unique integers in a sorted order
 */
interface SortedIntSet {

    fun put(value: Int)

    fun toArray(): Array<Int>
}