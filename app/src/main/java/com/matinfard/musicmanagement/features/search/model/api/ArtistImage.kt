package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName


data class ArtistImage (

	@SerializedName("#text") val url : String,
	@SerializedName("size") val size : String
)