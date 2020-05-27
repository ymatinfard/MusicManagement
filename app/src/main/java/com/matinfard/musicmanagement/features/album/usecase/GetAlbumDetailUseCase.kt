package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView

class GetAlbumDetailUseCase(private val repository: Repository) :
    UseCase<AlbumDetailView, GetAlbumDetailUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, AlbumDetailView> {
        return when (params.viewMode) {
            ViewMode.ONLINE -> repository.getAlbumDetail(params.artistName, params.albumName)
            ViewMode.OFFLINE -> repository.getSavedAlbumDetail(params.artistName, params.albumName)
        }

    }

    data class Params(val artistName: String, val albumName: String, val viewMode: ViewMode)
}