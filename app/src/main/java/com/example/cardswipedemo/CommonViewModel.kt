package com.example.cardswipedemo

import androidx.lifecycle.LiveData
import com.example.cardswipedemo.base.BaseViewModel
import com.example.cardswipedemo.models.Card
import com.example.cardswipedemo.models.User
import com.example.cardswipedemo.network.Resource
import com.example.cardswipedemo.repo.CommonRepository

class CommonViewModel(private val repository: CommonRepository) : BaseViewModel() {

    fun getCardData(): LiveData<Resource<Card>> {
        return repository.getCardData()
    }

    fun addToFavorites(user: User): LiveData<Long> {
        return repository.addUserToFavorites(user)
    }

    fun getFavoriteList(): LiveData<List<User>> {
        return repository.getFavoriteUserList()
    }
}