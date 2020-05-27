package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import com.google.gson.annotations.SerializedName
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailTrack


data class AlbumDetailTracks (

	@SerializedName("track") val track : List<AlbumDetailTrack>?
)