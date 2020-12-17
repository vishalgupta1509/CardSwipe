package com.example.cardswipedemo.models

import androidx.room.Entity

@Entity
data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
)