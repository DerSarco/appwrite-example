package com.dersarco.appwriteexample.appwrite

import io.appwrite.services.Realtime

class AppWriteRealtime(appWriteInstance: AppWriteInstance) {

    val realtime = Realtime(appWriteInstance.appWriteClient)

}
