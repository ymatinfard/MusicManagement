package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName


data class ArtistMatches (

	@SerializedName("artist") val artists : List<ArtistView>
)