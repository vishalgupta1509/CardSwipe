package com.example.cardswipedemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardswipedemo.models.User

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertUser(user: User): Long

    @Query("Select * From User")
    fun getFavoriteUserList(): LiveData<List<User>>
}