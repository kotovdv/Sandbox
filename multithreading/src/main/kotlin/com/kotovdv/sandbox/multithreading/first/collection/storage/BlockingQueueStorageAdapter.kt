package com.kotovdv.sandbox.multithreading.first.collection.storage

import java.util.concurrent.ArrayBlockingQueue

class BlockingQueueStorageAdapter<T>(capacity: Int = 10) : Storage<T> {
    private val blockingQueue = ArrayBlockingQueue<T>(capacity)

    override fun put(value: T) {
        blockingQueue.put(value)
    }

    override fun take(): T {
        return blockingQueue.take()
    }
}