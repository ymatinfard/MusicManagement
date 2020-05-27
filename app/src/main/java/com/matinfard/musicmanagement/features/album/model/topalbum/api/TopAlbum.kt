package com.matinfard.musicmanagement.features.album.model.topalbum.api

import com.google.gson.annotations.SerializedName

data class TopAlbum (
    @SerializedName("name") val name : String,
    @SerializedName("artist") val artist : TopAlbumArtist,
    @SerializedName("image") val image : List<TopAlbumImage>
)