package com.matinfard.musicmanagement.features.album.model.myalbum.db

import androidx.room.Embedded
import androidx.room.Relation
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity

data class ArtistWithAlbumsEntity(
    @Embedded
    val artist: ArtistEntity,
    @Relation(
        parentColumn = "artist_id",
        entityColumn = "album_artist_id"
    )
    val albumList: List<AlbumEntity>
)