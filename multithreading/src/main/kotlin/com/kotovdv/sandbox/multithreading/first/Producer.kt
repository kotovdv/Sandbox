package com.kotovdv.sandbox.multithreading.first

import com.kotovdv.sandbox.multithreading.first.collection.storage.Storage
import java.lang.Thread.interrupted
import java.lang.Thread.sleep

class Producer(val elementsQueue: Storage<Int>) : Runnable {
    override fun run() {
        while (!interrupted()) {
            val generateValue = generateValue()
            elementsQueue.put(generateValue)
            println("Producer ${Thread.currentThread().id} produced $generateValue")
            sleep(1000L)
        }
    }

    private fun generateValue() = java.util.concurrent.ThreadLocalRandom.current().nextInt(-999, 999)
}