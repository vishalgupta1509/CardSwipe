package com.example.cardswipedemo.database

import android.content.Context
import androidx.room.*
import com.example.cardswipedemo.models.User

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {

            val tempInstance = sInstance
            if (tempInstance != null)
                return tempInstance

            if (sInstance == null) {
                sInstance ?: synchronized(this) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "cardswipe.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return sInstance
        }
    }
}