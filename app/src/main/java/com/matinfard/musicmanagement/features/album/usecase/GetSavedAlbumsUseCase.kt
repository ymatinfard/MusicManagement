package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView

class GetSavedAlbumsUseCase(private val repository: Repository) :
    UseCase<List<TopAlbumView>, UseCase.None>() {

    override suspend fun run(params: None): Result<Failure, List<TopAlbumView>> =
        repository.getSavedAlbums()
}