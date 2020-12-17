package com.example.cardswipedemo.database

import androidx.room.TypeConverter
import com.example.cardswipedemo.models.Location
import com.example.cardswipedemo.models.Name
import com.example.cardswipedemo.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringToFavoriteUser(value: String): User {
        val objType = object : TypeToken<User>() {
        }.type
        return Gson().fromJson(value, objType)
    }

    @TypeConverter
    fun fromFavoriteUserToString(favoriteUser: User): String {
        val gson = Gson()
        return gson.toJson(favoriteUser)
    }

    @TypeConverter
    fun fromStringToLocation(value: String): Location {
        val objType = object : TypeToken<Location>() {
        }.type
        return Gson().fromJson(value, objType)
    }

    @TypeConverter
    fun fromLocationToString(location: Location): String {
        val gson = Gson()
        return gson.toJson(location)
    }

    @TypeConverter
    fun fromStringToName(value: String): Name {
        val objType = object : TypeToken<Name>() {
        }.type
        return Gson().fromJson(value, objType)
    }

    @TypeConverter
    fun fromNameToString(name: Name): String {
        val gson = Gson()
        return gson.toJson(name)
    }
}