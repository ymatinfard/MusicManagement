package com.matinfard.musicmanagement.core.data.local

import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity

interface LocalDataSource {

    fun saveAlbumInfo(artistEntity: ArtistEntity, albumEntity: AlbumEntity, trackEntityList: List<AlbumTrackEntity>): String
    fun checkAlbumExistence(artistEntity: ArtistEntity, albumEntity: AlbumEntity): String
    fun deleteAlbum(artistEntity: ArtistEntity, albumEntity: AlbumEntity)
    fun getAlbums(): List<TopAlbumView>
    fun getAlbumDetail(artistName: String, albumName: String): AlbumDetailView

}