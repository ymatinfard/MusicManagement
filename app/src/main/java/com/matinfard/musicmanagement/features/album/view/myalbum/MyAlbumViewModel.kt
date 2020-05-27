package com.matinfard.musicmanagement.features.album.view.myalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matinfard.musicmanagement.core.platform.BaseViewModel
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.usecase.GetSavedAlbumsUseCase
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import kotlinx.coroutines.launch

class MyAlbumViewModel(private val getSavedAlbumsUseCase: GetSavedAlbumsUseCase): BaseViewModel() {

    private val _albumList = MutableLiveData<List<TopAlbumView>>()
    val albumList: LiveData<List<TopAlbumView>> = _albumList

     fun loadAlbums(){
        viewModelScope.launch {
            getSavedAlbumsUseCase(UseCase.None()){ result ->
                result.fold(::handleFailure, ::handleGetSavedAlbumSuccess)
            }
        }
    }

    private fun handleGetSavedAlbumSuccess(list: List<TopAlbumView>) {
        _albumList.value = list.reversed()
    }

}