package com.dersarco.appwriteexample.di

import com.dersarco.appwriteexample.appwrite.*
import com.dersarco.appwriteexample.ui.screens.realtime.RealTimeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppWriteInstance(androidContext()) }
    single { AppWriteAccount(androidContext(), get()) }
    single { AppWriteDatabase(androidContext(), get()) }
    single { AppWriteStorage(androidContext(), get()) }
    single { AppWriteRealtime(get()) }
    viewModel { RealTimeViewModel(get(), get()) }
}
