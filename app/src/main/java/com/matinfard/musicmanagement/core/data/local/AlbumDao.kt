package com.matinfard.musicmanagement.core.data.local

import androidx.room.*
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumInfo
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailTrack
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.myalbum.db.ArtistWithAlbumsEntity

@Dao
interface AlbumDao {

    @Insert
    fun insertArtist(artist: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertTracks(tracks: List<AlbumTrackEntity>)

    @Query("SELECT artist_id  FROM artist_table WHERE artist_name = :artistName")
    fun getArtistId(artistName: String): String

    @Query("SELECT * FROM artist_table WHERE artist_name = :artistName")
    fun getArtistAlbums(artistName: String): ArtistWithAlbumsEntity

    @Query("DELETE  FROM track_table WHERE track_album_id = :trackAlbumId ")
    fun deleteTracks(trackAlbumId: String)

    @Query( "DELETE FROM album_table WHERE album_id = :albumId")
    fun deleteAlbum(albumId: String)

    @Query( "SELECT album_table.album_name AS albumName, album_table.album_img AS albumImage, artist_table.artist_name AS artistName FROM album_table INNER JOIN artist_table ON album_table.album_artist_id == artist_table.artist_id ")
    fun getAlbumAndArtist(): List<TopAlbumView>

    @Query( "SELECT  album_table.album_id AS albumId, album_table.album_img AS albumImg FROM album_table INNER JOIN artist_table ON album_table.album_artist_id == artist_table.artist_id WHERE artist_table.artist_name = :artistName AND album_table.album_name = :albumName" )
    fun getAlbumInfo(artistName: String, albumName: String): TopAlbumInfo

    @Query( "SELECT  album_table.album_id FROM album_table INNER JOIN artist_table ON album_table.album_artist_id == artist_table.artist_id WHERE artist_table.artist_name = :artistName AND album_table.album_name = :albumName" )
    fun getAlbumId(artistName: String, albumName: String): String

    @Query("Select track_name AS name FROM track_table WHERE track_album_id = :albumId")
    fun getAlbumTracks(albumId: String) : List<AlbumDetailTrack>

    @Transaction
    fun insertAlbumWithTracks(album: AlbumEntity, tracks: List<AlbumTrackEntity>) {
        insertAlbum(album)
        insertTracks(tracks)
    }

    @Transaction
    fun deleteAlbumWithTracks(albumId: String){
        deleteTracks(albumId)
        deleteAlbum(albumId)
    }

}