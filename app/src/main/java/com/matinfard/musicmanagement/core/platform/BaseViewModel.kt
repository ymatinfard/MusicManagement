package com.matinfard.musicmanagement.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _failure = SingleLiveEvent<Failure>()
    val failure: LiveData<Failure> = _failure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun loadingStarted() {
        _isLoading.value = true
    }

    protected fun loadingStopped(){
        _isLoading.value = false
    }

    protected fun handleFailure(failure: Failure) {
        _failure.call(failure)
        loadingStopped()
    }

}