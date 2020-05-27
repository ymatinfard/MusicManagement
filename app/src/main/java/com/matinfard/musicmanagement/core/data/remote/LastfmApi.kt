package com.matinfard.musicmanagement.core.data.remote

import com.matinfard.musicmanagement.core.platform.*
import com.matinfard.musicmanagement.features.search.model.api.ArtistSearchResponse
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumResponse
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastfmApi {

        @GET(URL)
        fun getArtist(
            @Query("method") method : String = METHOD_SEARCH
            , @Query("api_key") apiKey : String = API_KEY
            , @Query("format") format : String = FORMAT_JSON
            , @Query("limit") itemPerPage : Int = ITEM_PER_PAGE
            , @Query("artist") artistName : String
            , @Query("page") page : Int
        ): Call<ArtistSearchResponse>

    @GET(URL)
     fun getArtistTopAlbum(
        @Query("method") method: String = METHOD_TOP_ALBUM
        , @Query("api_key") apiKey : String = API_KEY
        , @Query("format") format : String = FORMAT_JSON
        , @Query("limit") itemPerPage : Int = ITEM_PER_PAGE
        , @Query("artist") artistName : String
        , @Query("page") page : Int
    ) : Call<TopAlbumResponse>

    @GET(URL)
     fun getTopAlbumDetail(
        @Query("method") method: String = METHOD_ALBUM_DETAIL
        , @Query("api_key") apiKey : String = API_KEY
        , @Query("format") format : String = FORMAT_JSON
        , @Query("artist") artistName : String
        , @Query("album") albumName : String
    ) : Call<AlbumDetailResponse>
}