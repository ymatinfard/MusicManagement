package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumsData

class GetTopAlbumUseCase(private val repository: Repository) :
    UseCase<TopAlbumsData, GetTopAlbumUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, TopAlbumsData> =
        repository.getArtistTopAlbum(params.artistName, params.pageNum)

    data class Params(val artistName : String, val pageNum : Int)
}