package com.matinfard.musicmanagement.features.search.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matinfard.musicmanagement.core.extention.empty

@Entity(tableName = "artist_table")
data class ArtistEntity (
    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    var artistId : String = String.empty(),
    @ColumnInfo(name = "artist_name")
    var artistName: String = String.empty())