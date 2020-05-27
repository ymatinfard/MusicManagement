package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import com.google.gson.annotations.SerializedName

data class AlbumDetail (

    @SerializedName("name") val name : String?,
    @SerializedName("artist") val artist : String?,
    @SerializedName("image") val image : List<AlbumDetailImage>?,
    @SerializedName("tracks") val tracks : AlbumDetailTracks?

)