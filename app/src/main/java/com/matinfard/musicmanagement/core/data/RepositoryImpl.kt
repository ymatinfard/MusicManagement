package com.matinfard.musicmanagement.core.data

import com.matinfard.musicmanagement.core.data.local.LocalDataSource
import com.matinfard.musicmanagement.core.data.remote.RemoteDataSource
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.NetworkHandler
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailResponse
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumResponse
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumsData
import com.matinfard.musicmanagement.features.search.model.api.ArtistSearchResponse
import com.matinfard.musicmanagement.features.search.model.api.ArtistsData
import org.koin.core.KoinComponent
import retrofit2.Call
import timber.log.Timber

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
    , private val localDataSource: LocalDataSource
    , private val networkHandler: NetworkHandler
) : Repository, KoinComponent {

    override fun getArtist(
        artistName: String,
        pageNum: Int
    ): Result<Failure, ArtistsData> {
        return when (networkHandler.isConnected()) {
            true ->
                request(
                    remoteDataSource.fetchArtist(artistName, pageNum),
                    { response -> response.toArtistsData() }
                    , ArtistSearchResponse.empty())

            false, null -> Result.Failure(
                Failure.NetworkConnection
            )
        }
    }

    override fun getArtistTopAlbum(
        artistName: String,
        pageNum: Int
    ): Result<Failure, TopAlbumsData> {
        return when (networkHandler.isConnected()) {
            true ->
                request(
                    remoteDataSource.fetchTopAlbum(artistName, pageNum),
                    { response -> response.toTopAlbumsData() }
                    , TopAlbumResponse.empty())
            false, null -> Result.Failure(
                Failure.NetworkConnection
            )
        }
    }

    override fun getAlbumDetail(
        artistName: String,
        albumName: String
    ): Result<Failure, AlbumDetailView> {
        return when (networkHandler.isConnected()) {
            true ->
                request(
                    remoteDataSource.fetchAlbumDetail(artistName, albumName),
                    { response -> response.toTopAlbumDetailView() }
                    , AlbumDetailResponse.empty())
            false, null -> Result.Failure(
                Failure.NetworkConnection
            )
        }
    }

    override fun saveAlbumDetail(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity,
        trackEntityList: List<AlbumTrackEntity>
    ): Result<Failure, String> =
        request {
            localDataSource.saveAlbumInfo(
                artistEntity,
                albumEntity,
                trackEntityList
            )
        }

    override fun checkAlbumExistence(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity
    ): Result<Failure, String> =
        request { localDataSource.checkAlbumExistence(artistEntity, albumEntity) }

    override fun deleteAlbum(
        artistEntity: ArtistEntity,
        albumEntity: AlbumEntity
    ): Result<Failure, Unit> =
        request { localDataSource.deleteAlbum(artistEntity, albumEntity) }

    override fun getSavedAlbums(): Result<Failure, List<TopAlbumView>> =
        request { localDataSource.getAlbums() }

    override fun getSavedAlbumDetail(
        artistName: String,
        albumName: String
    ): Result<Failure, AlbumDetailView> =
        request { localDataSource.getAlbumDetail(artistName, albumName) }

    private fun <T : Any, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Result<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Result.Success(transform((response.body() ?: default)))
                false -> Result.Failure(
                    Failure.ServerError
                )
            }
        } catch (exception: Throwable) {
            Timber.e(exception.message.toString())
            Result.Failure(Failure.ServerError)
        }
    }

    private fun <T> request(
        call: () -> (T)
    ): Result<Failure, T> {
        return try {
            val response = call.invoke()
            Result.Success(response)
        } catch (exception: Throwable) {
            Timber.e(exception.message.toString())
            Result.Failure(Failure.UnknownError)
        }
    }

}

