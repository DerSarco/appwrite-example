package com.dersarco.appwriteexample.appwrite

import android.content.Context
import io.appwrite.Client

open class AppWriteInstance(context: Context) {
    private val client = Client(context)
        .setEndpoint("https://192.168.3.2:4433/v1")
        .setProject("63f8c4cc0a8c1a64b76f")
        .setSelfSigned(true)

    fun getClient(): Client = client
}
