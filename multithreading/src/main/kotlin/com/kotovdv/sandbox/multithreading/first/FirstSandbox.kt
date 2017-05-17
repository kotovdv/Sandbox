package com.kotovdv.sandbox.multithreading.first

import com.kotovdv.sandbox.multithreading.first.collection.BinarySortedIntSet
import com.kotovdv.sandbox.multithreading.first.collection.SynchronizedSortedIntSet
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val blockingQueue = ArrayBlockingQueue<Int>(50)

    val threadPool = Executors.newFixedThreadPool(10)

    val set = SynchronizedSortedIntSet(BinarySortedIntSet())
    for (i in 0 until 5) {
        threadPool.submit(Producer(blockingQueue))
        threadPool.submit(Consumer(set, blockingQueue))
    }

    print("Press any key to stop execution...")
    readLine()

    threadPool.shutdownNow()
    threadPool.awaitTermination(1, TimeUnit.DAYS)

    println("Gathered the following array during execution")
    println(set.toArray().joinToString())
}