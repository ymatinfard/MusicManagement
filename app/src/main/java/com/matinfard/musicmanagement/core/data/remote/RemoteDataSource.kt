package com.matinfard.musicmanagement.core.data.remote

import com.matinfard.musicmanagement.features.search.model.api.ArtistSearchResponse
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumResponse
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailResponse
import retrofit2.Call

interface RemoteDataSource {
      fun fetchArtist(artistName: String, pageNum: Int): Call<ArtistSearchResponse>
      fun fetchTopAlbum(artistName: String, pageNum: Int) : Call<TopAlbumResponse>
      fun fetchAlbumDetail(artistName: String, albumName: String) : Call<AlbumDetailResponse>
}