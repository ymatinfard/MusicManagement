package com.matinfard.musicmanagement.features.album.model.albumdetail.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matinfard.musicmanagement.core.extention.empty

@Entity(tableName = "track_table")
data class AlbumTrackEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    var trackId : Int = 0,
    @ColumnInfo(name = "track_name")
    var trackName : String = String.empty(),
    @ColumnInfo(name = "track_album_id")
    var trackAlbumId : String = String.empty()
)