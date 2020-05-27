package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.features.search.model.api.ArtistImage

data class ArtistView (

	@SerializedName("name") val name : String,
	@SerializedName("image") val image : List<ArtistImage>
)