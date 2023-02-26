package com.dersarco.appwriteexample.appwrite

import android.content.Context
import io.appwrite.Client

open class AppWriteInstance(context: Context) {
    private val client = Client(context)
        .setEndpoint(ENDPOINT_URL)
        .setProject(PROJECT_ID)
        .setSelfSigned(true)

    fun getClient(): Client = client
}


// Replace this with your own project id and API url
private const val PROJECT_ID = "63f8c4cc0a8c1a64b76f"
private const val ENDPOINT_URL = "https://192.168.3.2:4433/v1"
