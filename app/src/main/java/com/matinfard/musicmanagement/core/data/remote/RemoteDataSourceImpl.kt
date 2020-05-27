package com.matinfard.musicmanagement.core.data.remote

import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailResponse
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumResponse
import com.matinfard.musicmanagement.features.search.model.api.ArtistSearchResponse
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Retrofit

class RemoteDataSourceImpl : RemoteDataSource, KoinComponent {

    private val retrofit: Retrofit by inject()

    private val retrofitService = retrofit.create(LastfmApi::class.java)

    override fun fetchArtist(artistName: String, pageNum: Int): Call<ArtistSearchResponse> =
        retrofitService.getArtist(artistName = artistName, page = pageNum)

    override fun fetchTopAlbum(
        artistName: String,
        pageNum: Int
    ): Call<TopAlbumResponse> =
        retrofitService.getArtistTopAlbum(artistName = artistName, page = pageNum)

    override fun fetchAlbumDetail(
        artistName: String,
        albumName: String
    ): Call<AlbumDetailResponse> =
        retrofitService.getTopAlbumDetail(artistName = artistName, albumName = albumName)


}


