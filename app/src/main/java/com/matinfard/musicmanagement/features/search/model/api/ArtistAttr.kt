package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName


data class ArtistAttr (

	@SerializedName("for") val forSearchString: String
)