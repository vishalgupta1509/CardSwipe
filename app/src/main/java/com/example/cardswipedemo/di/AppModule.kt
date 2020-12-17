package com.example.cardswipedemo.di

import androidx.room.Room
import com.example.cardswipedemo.CommonViewModel
import com.example.cardswipedemo.database.AppDatabase
import com.example.cardswipedemo.repo.CommonRepository
import com.example.cardswipedemo.util.AppExecutors
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val persistenceModule = module {
        single {
            Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "cardswipe.db")
                .build()
        }
        single { get<AppDatabase>().appDao() }
    }

    val viewModels = module {
        single { AppExecutors() }
        factory { CommonRepository(get()) }
        viewModel { CommonViewModel(get()) }
    }
}