package com.kotovdv.sandbox.multithreading.first

import com.kotovdv.sandbox.multithreading.first.collection.SortedIntSet
import java.lang.Thread.interrupted
import java.util.concurrent.BlockingQueue

class Consumer(val storage: SortedIntSet, val elementQueue: BlockingQueue<Int>) : Runnable {
    override fun run() {
        while (!interrupted()) {
            val nextElement = elementQueue.take()
            storage.put(nextElement)
            println("Consumer ${Thread.currentThread().id} consumed $nextElement")
        }
    }
}