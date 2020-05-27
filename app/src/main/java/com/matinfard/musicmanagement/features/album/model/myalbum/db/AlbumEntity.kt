package com.matinfard.musicmanagement.features.album.model.album.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matinfard.musicmanagement.core.extention.empty

@Entity(tableName = "album_table")
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    var albumId: String = String.empty(),
    @ColumnInfo(name = "album_artist_id")
    var albumArtistId: String = String.empty(),
    @ColumnInfo(name = "album_name")
    var albumName: String = String.empty(),
    @ColumnInfo(name = "album_img")
    var albumImg: String = String.empty()
)