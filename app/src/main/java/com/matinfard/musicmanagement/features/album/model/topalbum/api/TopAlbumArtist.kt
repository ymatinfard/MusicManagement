package com.matinfard.musicmanagement.features.album.model.topalbum.api

import com.google.gson.annotations.SerializedName

data class TopAlbumArtist (
	@SerializedName("name") val name : String
)