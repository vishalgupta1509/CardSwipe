package com.example.cardswipedemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val SSN: String,
    val cell: String,
    val dob: String,
    val email: String,
    val gender: String,
    val location: Location,
    val md5: String,
    val name: Name,
    val password: String,
    val phone: String,
    var picture: String,
    val registered: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String
) {
    @PrimaryKey(autoGenerate = true)
    var mID: Int = 0
}