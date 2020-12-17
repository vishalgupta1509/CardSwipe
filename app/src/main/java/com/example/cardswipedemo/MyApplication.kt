package com.example.cardswipedemo

import android.app.Application
import com.example.cardswipedemo.di.AppModule
import com.example.cardswipedemo.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                AppModule.persistenceModule,
                AppModule.viewModels,
                networkModule
            )
        }
    }
}