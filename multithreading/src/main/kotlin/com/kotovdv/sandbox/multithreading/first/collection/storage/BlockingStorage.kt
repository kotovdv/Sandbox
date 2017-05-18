package com.kotovdv.sandbox.multithreading.first.collection.storage

import java.util.*
import java.util.concurrent.locks.ReentrantLock


class BlockingStorage<T>(val capacity: Int = 10) {

    private val queue: Queue<T> = LinkedList<T>()

    private val lock = ReentrantLock()
    private val isFull = lock.newCondition()
    private val isEmpty = lock.newCondition()
    private var size = 0

    fun put(value: T) {
        lock.lock()
        try {
            while (isFull()) {
                isFull.await()
            }

            queue.offer(value)
            size++
            isEmpty.signal()
        } finally {
            lock.unlock()
        }

    }

    fun take(): T {
        lock.lock()
        try {
            while (isEmpty()) {
                isEmpty.await()
            }

            val nextElement = queue.poll()
            size--
            isFull.signal()

            return nextElement
        } finally {
            lock.unlock()
        }
    }

    private fun isFull() = size == capacity
    private fun isEmpty() = size == 0
}