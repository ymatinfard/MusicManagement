package com.matinfard.musicmanagement.core.data

import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.search.model.api.ArtistsData
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumsData
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity

interface Repository{
     fun getArtist(artistName: String, pageNum: Int): Result<Failure, ArtistsData>
     fun getArtistTopAlbum(artistName: String, pageNum: Int): Result<Failure, TopAlbumsData>
     fun getAlbumDetail(artistName: String, albumName: String): Result<Failure, AlbumDetailView>
     fun getSavedAlbumDetail(artistName: String, albumName: String): Result<Failure, AlbumDetailView>
     fun getSavedAlbums() : Result<Failure, List<TopAlbumView>>
     fun saveAlbumDetail(artistEntity: ArtistEntity, albumEntity: AlbumEntity, trackEntityList: List<AlbumTrackEntity> ): Result<Failure, String>
     fun checkAlbumExistence(artistEntity: ArtistEntity, albumEntity: AlbumEntity): Result<Failure, String>
     fun deleteAlbum(artistEntity: ArtistEntity, albumEntity: AlbumEntity): Result<Failure, Unit>
}