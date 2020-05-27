package com.matinfard.musicmanagement.features.album.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity

class SaveAlbumDetailUseCase(private val repository: Repository) :
    UseCase<String, SaveAlbumDetailUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, String> =
        repository.saveAlbumDetail(params.artistEntity, params.albumEntity, params.tracksEntityList)

    data class Params(val artistEntity: ArtistEntity, val albumEntity: AlbumEntity, val tracksEntityList: List<AlbumTrackEntity>)
}