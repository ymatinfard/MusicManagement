package com.matinfard.musicmanagement.features.search.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matinfard.musicmanagement.core.extention.empty
import com.matinfard.musicmanagement.core.platform.BaseViewModel
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.features.search.model.api.ArtistView
import com.matinfard.musicmanagement.features.search.model.api.ArtistsData
import com.matinfard.musicmanagement.features.search.usecase.GetArtistUseCase
import com.matinfard.musicmanagement.features.search.usecase.GetArtistUseCase.Params
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchArtistViewModel(private val getArtistUseCase: GetArtistUseCase) : BaseViewModel() {

    private val _artistList = MutableLiveData<List<ArtistView>>()
    val artistList: LiveData<List<ArtistView>> = _artistList

    private var artists = mutableListOf<ArtistView>()
    private var searchString = String.empty()
    private var itemsPerPage = 1
    private var totalItems = 1
    private var pageNum = 0

    fun searchArtist(searchString: String, pageNum: Int = 1) {
        loadingStarted()
        viewModelScope.launch {
            getArtistUseCase(Params(searchString, pageNum)) { result ->
                result.fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    fun loadMoreItems() {
        try {
            val isLastPage = (totalItems / itemsPerPage) == pageNum

            if (!(isLastPage || isLoading.value!!))
                searchArtist(searchString, pageNum + 1)

        } catch (ex: Exception) {
            Timber.e(ex.message.toString())
            handleFailure(Failure.UnknownError)
        }
    }

    private fun handleSuccess(domainModel: ArtistsData) {
        try {
            pageNum = domainModel.pageNum
            itemsPerPage = domainModel.itemsPerPage
            searchString = domainModel.searchString
            totalItems = domainModel.totalItems

            if (pageNum == 1) artists.clear()

            artists.addAll(domainModel.artist)
            _artistList.value = artists

            loadingStopped()
        } catch (ex: Exception) {
            Timber.e(ex.message.toString())
            handleFailure(Failure.UnknownError)
        }
    }

}