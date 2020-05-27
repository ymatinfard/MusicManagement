package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.features.search.model.api.ArtistAttr
import com.matinfard.musicmanagement.features.search.model.api.ArtistMatches
import com.matinfard.musicmanagement.features.search.model.api.ArtistQuery

data class ArtistQueryResult (

    @SerializedName("opensearch:Query") val query : ArtistQuery,
    @SerializedName("opensearch:totalResults") val totalResults : Int,
    @SerializedName("opensearch:itemsPerPage") val itemsPerPage : Int,
    @SerializedName("artistmatches") val artistMatches : ArtistMatches,
    @SerializedName("@attr") val attr : ArtistAttr
)
