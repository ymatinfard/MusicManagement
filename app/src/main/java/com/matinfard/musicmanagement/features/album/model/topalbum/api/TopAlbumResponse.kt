package com.matinfard.musicmanagement.features.album.model.topalbum.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.core.extention.empty


data class TopAlbumResponse(
    @SerializedName("topalbums") val topAlbums: TopAlbums
) {
    fun toTopAlbumsData(): TopAlbumsData {
        val topAlbumsDomain = topAlbums.album.map {
            TopAlbumView(
                it.artist.name,
                it.name,
                it.image[0].url
            )
        }
        return TopAlbumsData(
            topAlbumsDomain,
            topAlbums.attr.page,
            topAlbums.attr.totalPages
        )
    }

    companion object {
        fun empty() =
            TopAlbumResponse(
                TopAlbums(
                    emptyList()
                    ,
                    TopAlbumAttr(
                        String.empty(),
                        0,
                        0,
                        0,
                        0
                    )
                )
            )
    }
}