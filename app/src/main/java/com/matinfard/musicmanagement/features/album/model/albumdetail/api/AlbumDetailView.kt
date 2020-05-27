package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailTrack

data class AlbumDetailView(val artistName: String, val albumName: String, val imageUrl: String, val tracks: List<AlbumDetailTrack>)