package com.example.cardswipedemo.network

import androidx.lifecycle.LiveData
import com.example.cardswipedemo.models.Card
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun getCardData(@Url cardURL: String): LiveData<ApiResponse<Card>>
}