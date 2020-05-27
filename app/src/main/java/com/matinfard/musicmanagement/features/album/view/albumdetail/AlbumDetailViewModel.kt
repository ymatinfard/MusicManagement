package com.matinfard.musicmanagement.features.album.view.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matinfard.musicmanagement.core.extention.empty
import com.matinfard.musicmanagement.core.platform.BaseViewModel
import com.matinfard.musicmanagement.core.platform.SUCCESS
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.album.usecase.CheckAlbumExistenceUseCase
import com.matinfard.musicmanagement.features.album.usecase.DeleteAlbumUseCase
import com.matinfard.musicmanagement.features.album.usecase.GetAlbumDetailUseCase
import com.matinfard.musicmanagement.features.album.usecase.SaveAlbumDetailUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumDetailViewModel(
    private val getTopAlbumDetailUseCase: GetAlbumDetailUseCase
    , private val saveTopAlbumDetailUseCase: SaveAlbumDetailUseCase
    , private val checkAlbumExistenceUseCase: CheckAlbumExistenceUseCase
    , private val deleteAlbumUseCase: DeleteAlbumUseCase
) : BaseViewModel() {

    private val _albumDetailData = MutableLiveData<AlbumDetailView>()
    val albumDetailList: LiveData<AlbumDetailView> = _albumDetailData

    private val _isAlbumExisting = MutableLiveData(true)
    val isAlbumExisting: LiveData<Boolean> = _isAlbumExisting

    private val _albumDeleteStatus = MutableLiveData<Boolean>()
    val albumDeleteStatus: LiveData<Boolean> = _albumDeleteStatus

    private val _albumSaveStatue = MutableLiveData<Boolean>()
    val albumSaveStatus: LiveData<Boolean> = _albumSaveStatue

    private lateinit var mTopAlbumDetailData: AlbumDetailView


    fun loadMovieDetails(artistName: String, albumName: String, viewMode: ViewMode) {

        viewModelScope.launch {
            getTopAlbumDetailUseCase(
                GetAlbumDetailUseCase.Params(
                    artistName,
                    albumName,
                    viewMode
                )
            ) { result ->
                result.fold(::handleFailure, ::handleGetTopAlbumSuccess)
            }

            if (viewMode == ViewMode.ONLINE)
                checkAlbumExistence(
                    ArtistEntity(
                        artistName = artistName
                    )
                    ,
                    AlbumEntity(
                        albumName = albumName
                    )
                )
        }
    }

    private fun handleGetTopAlbumSuccess(topAlbumDetailDomainModel: AlbumDetailView) {
        mTopAlbumDetailData = topAlbumDetailDomainModel
        _albumDetailData.value = topAlbumDetailDomainModel

    }

    fun saveOrDeleteAlbum() {
        try {
            if (isAlbumExisting.value!!) {
                deleteAlbum(
                    ArtistEntity(
                        artistName = mTopAlbumDetailData.artistName
                    )
                    ,
                    AlbumEntity(
                        albumName = mTopAlbumDetailData.albumName
                    )
                )
            } else {
                saveAlbum(
                    ArtistEntity(
                        artistName = mTopAlbumDetailData.artistName
                    )
                    ,
                    AlbumEntity(
                        albumName = mTopAlbumDetailData.albumName,
                        albumImg = mTopAlbumDetailData.imageUrl
                    )
                    , mTopAlbumDetailData.tracks.map {
                        AlbumTrackEntity(
                            trackName = it.name
                        )
                    })
            }
        } catch (ex: Exception) {
            Timber.e(ex.message)
        }
    }

    private fun saveAlbum(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity,
        trackEntityList: List<AlbumTrackEntity>
    ) {
        viewModelScope.launch {
            saveTopAlbumDetailUseCase(
                SaveAlbumDetailUseCase.Params(
                    artistEntity,
                    albumEntity,
                    trackEntityList
                )
            ) { result ->
                result.fold(::handleFailure, ::handleSaveAlbumSuccess)
            }
        }
    }

    private fun handleSaveAlbumSuccess(albumId: String) {
        setAlbumIdExisting(albumId)
        _albumSaveStatue.value = SUCCESS
    }

    private fun checkAlbumExistence(artistEntity: ArtistEntity, albumEntity: AlbumEntity) {
        viewModelScope.launch {
            checkAlbumExistenceUseCase(
                CheckAlbumExistenceUseCase.Params(
                    artistEntity,
                    albumEntity
                )
            ) { result ->
                result.fold(::handleFailure, ::handleCheckAlbumExistenceSuccess)
            }
        }
    }

    private fun handleCheckAlbumExistenceSuccess(savedAlbumId: String) {
        setAlbumIdExisting(savedAlbumId)
    }

    private fun deleteAlbum(artistEntity: ArtistEntity, albumEntity: AlbumEntity) {
        viewModelScope.launch {
            deleteAlbumUseCase(
                DeleteAlbumUseCase.Params(
                    artistEntity
                    , albumEntity
                )
            ) { result ->
                result.fold(::handleFailure, ::handleDeleteAlbumSuccess)
            }
        }
    }

    private fun handleDeleteAlbumSuccess(unit: Unit) {
        setAlbumIdExisting(String.empty())
        _albumDeleteStatus.value = SUCCESS
    }

    private fun setAlbumIdExisting(albumId: String) {
        _isAlbumExisting.value = albumId.isNotEmpty()
    }

}