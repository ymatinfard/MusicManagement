package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity

class DeleteAlbumUseCase(private val repository: Repository) :
    UseCase<Unit, DeleteAlbumUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, Unit> =
        repository.deleteAlbum(params.artistEntity, params.albumEntity)

    data class Params(val artistEntity: ArtistEntity, val albumEntity: AlbumEntity)
}