package com.matinfard.musicmanagement.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matinfard.musicmanagement.core.data.local.AlbumDatabase
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    private lateinit var database: AlbumDatabase

    @Before
    fun initDatabase(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext()
            , AlbumDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase(){
        database.close()
    }

    // `back tick is not supported for function name`
    @Test
    fun insert_album_with_tracks_and_fetch_album_tracks(){
        val albumId = "ab-cd"
        val trackName = "track_name"
        val album =
            AlbumEntity(
                albumId,
                "1",
                "name",
                "url"
            )
        val track =
            AlbumTrackEntity(
                123,
                trackName,
                albumId
            )

        database.albumDao().insertAlbumWithTracks(album, listOf(track))

        val fetched = database.albumDao().getAlbumTracks(albumId)

        fetched[0].name shouldBeEqualTo trackName
    }

    @Test
    fun insert_artist_and_get_artist_id(){
        val artistId = "ab-cd"
        val artistName = "name"
        val artist =
            ArtistEntity(
                artistId,
                artistName
            )

        database.albumDao().insertArtist(artist)

        val fetchedId = database.albumDao().getArtistId(artistName)

        fetchedId shouldBeEqualTo artistId
    }
}