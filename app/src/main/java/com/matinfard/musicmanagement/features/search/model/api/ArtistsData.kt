package com.matinfard.musicmanagement.features.search.model.api

data class ArtistsData (val artist : List<ArtistView>, val searchString : String, val itemsPerPage : Int, val pageNum : Int, val totalItems : Int)

