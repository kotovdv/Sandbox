package com.kotovdv.sandbox.jmm

import com.sun.management.GarbageCollectionNotificationInfo
import com.sun.management.GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION
import java.lang.management.ManagementFactory
import javax.management.NotificationEmitter
import javax.management.NotificationListener
import javax.management.openmbean.CompositeData

class GCMonitor {

    fun start() {
        val gcBeans = ManagementFactory.getGarbageCollectorMXBeans()
        for (currentBean in gcBeans) {
            println("Registering listener for ${currentBean.name}")
            if (currentBean is NotificationEmitter) {
                val notificationListener = createNotificationListener()

                currentBean.addNotificationListener(notificationListener, null, null)
            }
        }
    }

    private fun createNotificationListener(): NotificationListener {
        return NotificationListener { notification, _ ->
            if (GARBAGE_COLLECTION_NOTIFICATION == notification.type) {
                val userData = notification.userData
                if (userData is CompositeData) {
                    val info = GarbageCollectionNotificationInfo.from(userData)

                    println("GC INFO \n" +
                            "ID : [${info.gcInfo.id}]\n" +
                            "TYPE : [${info.gcAction}]\n" +
                            "NAME: [${info.gcName}]\n" +
                            "CAUSE: [${info.gcCause}]\n" +
                            "DURATION : [${info.gcInfo.duration}\n")
                }
            }
        }
    }
}