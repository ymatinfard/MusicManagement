package com.matinfard.musicmanagement.features.search.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.search.model.api.ArtistsData

class GetArtistUseCase(private val repository: Repository):
    UseCase<ArtistsData, GetArtistUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, ArtistsData> =
        repository.getArtist(params.searchString, params.pageNum)

    data class Params(val searchString : String, val pageNum : Int)
}