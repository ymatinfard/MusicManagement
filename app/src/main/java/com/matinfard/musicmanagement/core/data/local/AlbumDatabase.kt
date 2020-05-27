package com.matinfard.musicmanagement.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity

@Database(
    entities = [ArtistEntity::class, AlbumEntity::class, AlbumTrackEntity::class]
    , version = 1
)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao() : AlbumDao
}