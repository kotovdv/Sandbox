package com.kotovdv.sandbox.jmm

import com.kotovdv.sandbox.exceptions.jmm.NoListenerAssociatedException
import net.jcip.annotations.NotThreadSafe
import java.io.OutputStream
import java.lang.management.ManagementFactory.getGarbageCollectorMXBeans
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.management.NotificationEmitter


/**
 * Simple GC information logger example
 *
 * @author Dmitriy Kotov
 * @NotThreadSafe was not yet tested in multithreaded env
 */

@NotThreadSafe
object GCLogger {

    private val listeners: ConcurrentMap<OutputStream, GCListener> = ConcurrentHashMap()

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