package com.dersarco.appwriteexample.appwrite

import android.content.Context
import com.dersarco.appwriteexample.R
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.models.Account as AccountResponse

class AppWriteAccount(private val context: Context) : AppWriteInstance(context) {

    private val account = Account(client = appWriteClient)

    suspend fun registerUser(
        name: String,
        password: String,
        email: String
    ): AppWriteResponse<AccountResponse<Any>> {
        try {
            if (password.length < 8) {
                return AppWriteResponse.Error(8008, "Password must be 8 characters long.")
            }
            val user = account.create(
                userId = ID.unique(),
                email = email,
                password = password,
                name = name
            )
            return AppWriteResponse.Success(user)
        } catch (e: AppwriteException) {
            e.printStackTrace()
            return AppWriteResponse.Error(
                e.code ?: 0,
                e.message ?: context.getString(R.string.generic_error)
            )
        }
    }

    suspend fun loginUser(email: String, password: String): AppWriteResponse<AccountResponse<Any>> {
        return try {
            account.createEmailSession(email, password)
            AppWriteResponse.Success(account.get())
        } catch (e: AppwriteException) {
            e.printStackTrace()
            AppWriteResponse.Error(
                e.code ?: 0,
                e.message ?: context.getString(R.string.generic_error)
            )
        }
    }
}
