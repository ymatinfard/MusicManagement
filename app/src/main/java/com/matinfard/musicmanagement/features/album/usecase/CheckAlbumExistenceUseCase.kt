package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity

class CheckAlbumExistenceUseCase(private val repository: Repository) :
    UseCase<String, CheckAlbumExistenceUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, String> =
        repository.checkAlbumExistence(params.artistEntity, params.albumEntity)

    data class Params(val artistEntity: ArtistEntity, val albumEntity: AlbumEntity)
}