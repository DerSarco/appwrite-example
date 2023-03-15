package com.dersarco.appwriteexample.appwrite

import io.appwrite.models.RealtimeResponseEvent
import io.appwrite.models.RealtimeSubscription
import io.appwrite.services.Realtime

class AppWriteRealtime(appWriteInstance: AppWriteInstance) {

    private val realtime = Realtime(appWriteInstance.appWriteClient)

    fun <T : Any> realtimeSubscription(
        entity: Class<T>,
        realtimeCallbackFun: (RealtimeResponseEvent<out T>) -> Unit
    ): RealtimeSubscription {
        return realtime.subscribe(
            "databases.63fba37bbf99b2eae6fb.collections.63fba37f873c649fbdf8.documents",
            payloadType = entity,
        ) {
            realtimeCallbackFun(it)
        }
    }

}
