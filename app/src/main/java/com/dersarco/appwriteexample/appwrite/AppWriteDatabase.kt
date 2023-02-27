package com.dersarco.appwriteexample.appwrite

import android.content.Context
import com.dersarco.appwriteexample.R
import com.dersarco.appwriteexample.data.entities.Person
import com.google.gson.Gson
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.services.Databases

class AppWriteDatabase(private val context: Context, appWriteInstance: AppWriteInstance) {

    //Replace with your own id's from AppWrite database
    private object AppWriteDatabaseConstants {
        const val DATABASE_ID = "63fba37bbf99b2eae6fb"
        const val COLLECTION_ID = "63fba37f873c649fbdf8"
    }

    private val database = Databases(appWriteInstance.appWriteClient)

    //TODO: Search how to create databases programatically

    suspend fun createDocument(person: Person): AppWriteResponse<Document<Any>> {
        return try {
            val response = database.createDocument(
                databaseId = AppWriteDatabaseConstants.DATABASE_ID,
                collectionId = AppWriteDatabaseConstants.COLLECTION_ID,
                documentId = ID.unique(),
                data = person
            )
            AppWriteResponse.Success(response)

        } catch (e: AppwriteException) {
            e.printStackTrace()
            AppWriteResponse.Error(
                e.code ?: 0,
                e.message ?: context.getString(R.string.generic_error)
            )
        }
    }

    suspend fun listDocuments(): AppWriteResponse<com.dersarco.appwriteexample.data.entities.DocumentList> {
        return try {
            val response = database.listDocuments(
                databaseId = AppWriteDatabaseConstants.DATABASE_ID,
                collectionId = AppWriteDatabaseConstants.COLLECTION_ID
            )
            if(response.documents.isEmpty()){
                throw AppwriteException("No data found", 8888)
            }
            val dataParsed = Gson().fromJson(
                Gson().toJson(response),
                com.dersarco.appwriteexample.data.entities.DocumentList::class.java
            )
            AppWriteResponse.Success(dataParsed)
        } catch (e: AppwriteException) {
            e.printStackTrace()
            AppWriteResponse.Error(
                e.code ?: 0,
                e.message ?: context.getString(R.string.generic_error)
            )
        }
    }
}
