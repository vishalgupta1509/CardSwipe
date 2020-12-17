package com.example.cardswipedemo.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

open class BaseViewModel : ViewModel() {

    private val vmScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override fun onCleared() {
        super.onCleared()
        vmScope.coroutineContext.cancelChildren()
    }
}