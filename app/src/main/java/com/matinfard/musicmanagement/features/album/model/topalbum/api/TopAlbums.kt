package com.matinfard.musicmanagement.features.album.model.topalbum.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbum
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumAttr

data class TopAlbums (
    @SerializedName("album") val album : List<TopAlbum>,
    @SerializedName("@attr") val attr : TopAlbumAttr
)