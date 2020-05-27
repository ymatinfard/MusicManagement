package com.matinfard.musicmanagement.core.data.local

import com.matinfard.musicmanagement.core.extention.empty
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import java.util.*

class LocalDataSourceImpl(private val albumDatabase: AlbumDatabase) : LocalDataSource {

    override  fun saveAlbumInfo(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity,
        trackEntityList: List<AlbumTrackEntity>
    ): String {

        val albumId = UUID.randomUUID().toString()
        var artistId = albumDatabase.albumDao().getArtistId(artistEntity.artistName)

        if (artistId.isNullOrEmpty()) {
            artistId = UUID.randomUUID().toString()
            artistEntity.artistId = artistId
            albumDatabase.albumDao().insertArtist(artistEntity)
        }

        albumEntity.albumArtistId = artistId
        albumEntity.albumId = albumId
        trackEntityList.forEach { it.trackAlbumId = albumId }
        albumDatabase.albumDao().insertAlbumWithTracks(albumEntity, trackEntityList)
        return albumId

    }

    override  fun checkAlbumExistence(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity
    ): String {
        val albumId : String? = albumDatabase.albumDao().getAlbumId(artistEntity.artistName, albumEntity.albumName)
        return albumId ?: String.empty()
    }

    override  fun deleteAlbum(artistEntity: ArtistEntity, albumEntity: AlbumEntity) {
        val albumId : String = albumDatabase.albumDao().getAlbumId(artistEntity.artistName, albumEntity.albumName)
        albumDatabase.albumDao().deleteAlbumWithTracks(albumId)
    }

    override  fun getAlbums(): List<TopAlbumView> {
        return albumDatabase.albumDao().getAlbumAndArtist()
    }

    override fun getAlbumDetail(
        artistName: String,
        albumName: String
    ): AlbumDetailView {
        val albumInfo = albumDatabase.albumDao().getAlbumInfo(artistName, albumName)
        val albumTrackList = albumDatabase.albumDao().getAlbumTracks(albumInfo.albumId)
        return AlbumDetailView(
            artistName,
            albumName,
            albumInfo.albumImg,
            albumTrackList
        )
    }


}