package com.matinfard.musicmanagement.features.album.model.topalbum.api

import com.google.gson.annotations.SerializedName

data class TopAlbumAttr (
	@SerializedName("artist") val artist : String,
	@SerializedName("page") val page : Int,
	@SerializedName("perPage") val perPage : Int,
	@SerializedName("totalPages") val totalPages : Int,
	@SerializedName("total") val total : Int
)