package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName

data class ArtistQuery (

	@SerializedName("startPage") val startPage : Int
)