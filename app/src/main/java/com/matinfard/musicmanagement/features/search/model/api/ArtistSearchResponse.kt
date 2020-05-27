package com.matinfard.musicmanagement.features.search.model.api

import com.google.gson.annotations.SerializedName


data class ArtistSearchResponse (

	@SerializedName("results") val results : ArtistQueryResult
){
	fun toArtistsData() : ArtistsData {
		return ArtistsData(
            results.artistMatches.artists,
            results.attr.forSearchString,
            results.itemsPerPage,
            results.query.startPage,
            results.totalResults
        )
	}

    companion object{
        fun empty() =
            ArtistSearchResponse(
                ArtistQueryResult(
                    ArtistQuery(
                        0
                    )
                    , 0, 0,
                    ArtistMatches(
                        emptyList()
                    )
                    ,
                    ArtistAttr(
                        ""
                    )
                )
            )
    }
}