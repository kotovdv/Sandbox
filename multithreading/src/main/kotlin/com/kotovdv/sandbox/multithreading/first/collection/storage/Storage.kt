package com.kotovdv.sandbox.multithreading.first.collection.storage

interface Storage<T> {

    fun put(value: T)

    fun take(): T
}