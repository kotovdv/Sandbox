package com.kotovdv.sandbox.jvm

import com.kotovdv.sandbox.exceptions.jvm.NoListenerAssociatedException
import net.jcip.annotations.NotThreadSafe
import java.io.OutputStream
import java.lang.management.ManagementFactory.getGarbageCollectorMXBeans
import javax.management.NotificationEmitter

@NotThreadSafe
object GCLogger {

    private val listeners: MutableMap<OutputStream, GCListener> = HashMap()

    fun enable(vararg streams: OutputStream = arrayOf(System.out)) {
        streams.forEach { currentStream ->
            val listener = GCListener(currentStream)

            val previousListener = listeners.putIfAbsent(currentStream, listener)
            if (previousListener == null) {
                register(listener)
            }
        }
    }

    fun disable(vararg streams: OutputStream = listeners.keys.toTypedArray()) {
        streams.forEach { currentStream ->
            val listener = listeners.remove(currentStream)
                    ?: throw NoListenerAssociatedException(currentStream)

            unregister(listener)
        }
    }

    private fun register(listener: GCListener) {
        getGarbageCollectorMXBeans()
                .filterIsInstance<NotificationEmitter>()
                .forEach { it.addNotificationListener(listener, null, null) }
    }

    private fun unregister(listener: GCListener) {
        getGarbageCollectorMXBeans()
                .filterIsInstance<NotificationEmitter>()
                .forEach({ it.removeNotificationListener(listener) })
    }
}