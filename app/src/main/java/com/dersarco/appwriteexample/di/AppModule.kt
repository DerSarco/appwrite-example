package com.dersarco.appwriteexample.di

import com.dersarco.appwriteexample.appwrite.AppWriteAccount
import com.dersarco.appwriteexample.appwrite.AppWriteDatabase
import com.dersarco.appwriteexample.appwrite.AppWriteInstance
import com.dersarco.appwriteexample.appwrite.AppWriteStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AppWriteInstance(androidContext()) }
    single { AppWriteAccount(androidContext(), get()) }
    single { AppWriteDatabase(androidContext(), get()) }
    single { AppWriteStorage(androidContext(), get()) }
}
