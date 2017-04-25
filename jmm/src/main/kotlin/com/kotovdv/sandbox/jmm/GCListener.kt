package com.kotovdv.sandbox.jmm

import com.sun.management.GarbageCollectionNotificationInfo
import com.sun.management.GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION
import java.io.OutputStream
import javax.management.Notification
import javax.management.NotificationListener
import javax.management.openmbean.CompositeData

class GCListener(stream: OutputStream) : NotificationListener {

    private val writer = stream.writer()

    override fun handleNotification(notification: Notification?, handback: Any?) {
        if (notification != null && isGCNotification(notification)) {
            val userData = notification.userData
            if (userData is CompositeData) {
                val info = GarbageCollectionNotificationInfo.from(userData)

                writer.write(prepareLogMessage(info))
            }
        }
    }

    private fun isGCNotification(notification: Notification): Boolean {
        return GARBAGE_COLLECTION_NOTIFICATION == notification.type
    }

    private fun prepareLogMessage(info: GarbageCollectionNotificationInfo): String {
        return "GC INFO \n" +
                "ID : [${info.gcInfo.id}]\n" +
                "TYPE : [${info.gcAction}]\n" +
                "NAME: [${info.gcName}]\n" +
                "CAUSE: [${info.gcCause}]\n" +
                "DURATION : [${info.gcInfo.duration}\n"
    }
}