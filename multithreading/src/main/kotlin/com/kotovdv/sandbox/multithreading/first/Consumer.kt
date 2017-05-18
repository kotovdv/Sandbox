package com.kotovdv.sandbox.multithreading.first

import com.kotovdv.sandbox.multithreading.first.collection.SortedIntSet
import com.kotovdv.sandbox.multithreading.first.collection.storage.Storage
import java.lang.Thread.interrupted

class Consumer(val storage: SortedIntSet, val elementQueue: Storage<Int>) : Runnable {
    override fun run() {
        while (!interrupted()) {
            val nextElement = elementQueue.take()
            storage.put(nextElement)
            println("Consumer ${Thread.currentThread().id} consumed $nextElement")
        }
    }
}