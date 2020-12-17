package com.example.cardswipedemo.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardswipedemo.database.AppDatabase
import com.example.cardswipedemo.models.Card
import com.example.cardswipedemo.models.User
import com.example.cardswipedemo.network.ApiInterface
import com.example.cardswipedemo.network.Resource
import com.example.cardswipedemo.network.networkResource.NetworkBoundResource
import com.example.cardswipedemo.util.AppExecutors
import org.koin.java.KoinJavaComponent

class CommonRepository(val database: AppDatabase) {
    val apiInterface        : ApiInterface by KoinJavaComponent.inject(ApiInterface::class.java)
    val appExecutors        : AppExecutors by KoinJavaComponent.inject(AppExecutors::class.java)

    fun getCardData(): LiveData<Resource<Card>> {
        val url = "https://randomuser.me/api/0.4/?randomapi"

        return object : NetworkBoundResource<Card, Card>(appExecutors) {
            override fun createCall() = apiInterface.getCardData(url)
            override fun onFetchFailed() {
                Log.e(CommonRepository::class.java.simpleName, "onFetchFailed DefaultMeasurementRanges")
            }

        }.asLiveData()
    }

    fun addUserToFavorites(user: User): LiveData<Long> {
        val mutableData = MutableLiveData<Long>()
        appExecutors.diskIO().execute {
            mutableData.postValue(database.appDao().insertUser(user))
        }

        return mutableData
    }

    fun getFavoriteUserList(): LiveData<List<User>> {
        return database.appDao().getFavoriteUserList()
    }
}