@file:Suppress("KotlinDeprecation")

package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.core.extention.empty
import com.matinfard.musicmanagement.core.platform.NO_VALUE_PROVIVDED

data class AlbumDetailResponse(

    @SerializedName("album") val album: AlbumDetail
) {
    fun toTopAlbumDetailView(): AlbumDetailView {
        return AlbumDetailView(
            album.artist ?: NO_VALUE_PROVIVDED,
            album.name ?: NO_VALUE_PROVIVDED,
            album?.image?.get(0)?.url ?: NO_VALUE_PROVIVDED,
            album.tracks?.track ?: emptyList()
        )
    }

    companion object {
        fun empty() =
            AlbumDetailResponse(
                AlbumDetail(
                    String.empty()
                    ,
                    String.empty()
                    ,
                    listOf(
                        AlbumDetailImage(
                            String.empty(),
                            String.empty()
                        )
                    )
                    ,
                    AlbumDetailTracks(
                        emptyList()
                    )
                )
            )
    }
}