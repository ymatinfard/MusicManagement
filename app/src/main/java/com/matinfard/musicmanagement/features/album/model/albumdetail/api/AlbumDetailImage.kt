package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import com.google.gson.annotations.SerializedName

data class AlbumDetailImage (

	@SerializedName("#text") val url : String,
	@SerializedName("size") val size : String
)