package com.matinfard.musicmanagement.features.album.view.topalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matinfard.musicmanagement.core.extention.empty
import com.matinfard.musicmanagement.core.platform.BaseViewModel
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumsData
import com.matinfard.musicmanagement.features.album.usecase.GetTopAlbumUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class TopAlbumViewModel(private val getTopAlbumUseCase: GetTopAlbumUseCase) : BaseViewModel() {

    private val _topAlbumList = MutableLiveData<List<TopAlbumView>>()
    val topAlbumList: LiveData<List<TopAlbumView>> = _topAlbumList

    private var topAlbums = mutableListOf<TopAlbumView>()

    private var totalPageNumber: Int = 1
    private var currentPageNumber: Int = 1
    private var artistName = String.empty()

    fun loadAlbum(artistName: String) {
        if (firstTimeCall())
            getTopAlbum(artistName)
    }

    private fun firstTimeCall(): Boolean = topAlbums.size == 0

    private fun getTopAlbum(artistName: String, pageNum: Int = 1) {
        loadingStarted()
        viewModelScope.launch {
            getTopAlbumUseCase(GetTopAlbumUseCase.Params(artistName, pageNum)) { result ->
                result.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleSuccess(topAlbumData: TopAlbumsData) {
        try {
            topAlbums.addAll(topAlbumData.albums)
            totalPageNumber = topAlbumData.totalPage
            currentPageNumber = topAlbumData.pageNum
            artistName = topAlbumData.albums[0].artistName
            _topAlbumList.value = topAlbums
        } catch (ex: Exception) {
            Timber.e(ex.message)
            handleFailure(Failure.UnknownError)
        } finally {
            loadingStopped()
        }

    }

    fun loadMoreItems() {
        try {
            val isLastPage = totalPageNumber == currentPageNumber
            if (!(isLastPage || isLoading.value!!))
                getTopAlbum(artistName, currentPageNumber + 1)
        } catch (ex: Exception) {
            Timber.e(ex.message)
            handleFailure(Failure.UnknownError)
        }
    }



}