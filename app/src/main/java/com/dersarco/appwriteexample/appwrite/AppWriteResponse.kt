package com.dersarco.appwriteexample.appwrite

sealed class AppWriteResponse<T: Any> {
    class Success<T: Any>(val data: T): AppWriteResponse<T>()
    class Error<T: Any>(val code: Int, val message: String): AppWriteResponse<T>()
}
