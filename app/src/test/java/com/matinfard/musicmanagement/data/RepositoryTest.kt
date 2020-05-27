package com.matinfard.musicmanagement.data

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.data.RepositoryImpl
import com.matinfard.musicmanagement.core.data.local.LocalDataSource
import com.matinfard.musicmanagement.core.data.remote.RemoteDataSource
import com.matinfard.musicmanagement.core.platform.Failure
import com.matinfard.musicmanagement.core.platform.NetworkHandler
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.*
import com.matinfard.musicmanagement.features.search.model.api.*
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private lateinit var repository: Repository

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var artistResponse: Response<ArtistSearchResponse>

    @Mock
    private lateinit var albumDetailResponse: Response<AlbumDetailResponse>

    @Mock
    private lateinit var artistCall: Call<ArtistSearchResponse>

    @Mock
    private lateinit var albumDetailCall: Call<AlbumDetailResponse>

    private val artistSearchResponseTest =
        ArtistSearchResponse(
            ArtistQueryResult(
                ArtistQuery(1), 1
                , 1
                ,
                ArtistMatches(
                    listOf(
                        ArtistView(
                            "tst", listOf(
                                ArtistImage(
                                    "url",
                                    "1"
                                )
                            )
                        )
                    )
                ),
                ArtistAttr("tst")
            )
        )

    private val albumDetailResponseTest =
        AlbumDetailResponse(
            AlbumDetail(
                "tst", "tst", listOf(
                    AlbumDetailImage(
                        "url",
                        "50"
                    )
                ),
                AlbumDetailTracks(
                    listOf(
                        AlbumDetailTrack(
                            "tst"
                        )
                    )
                )
            )
        )

    private val artistDataTest =
        ArtistsData(
            listOf(
                ArtistView(
                    "tst",
                    listOf(
                        ArtistImage(
                            "url",
                            "1"
                        )
                    )
                )
            ), "tst", 1, 1, 1
        )

    @Before
    fun setup() {
        repository = RepositoryImpl(remoteDataSource, localDataSource, networkHandler)
    }

    @Test
    fun `getArtist should return empty list when server response is null`() {
        given { networkHandler.isConnected() }.willReturn(true)
        given { artistResponse.body() }.willReturn(null)
        given { artistResponse.isSuccessful }.willReturn(true)
        given { artistCall.execute() }.willReturn(artistResponse)
        given { remoteDataSource.fetchArtist("tst", 1) }.willReturn(artistCall)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeEqualTo Result.Success(ArtistSearchResponse.empty().toArtistsData())
        Mockito.verify(remoteDataSource).fetchArtist("tst", 1)
    }

    @Test
    fun `getArtist should return artists list from server`() {
        given { networkHandler.isConnected() }.willReturn(true)
        given { artistResponse.body() }.willReturn(artistSearchResponseTest)
        given(artistResponse.isSuccessful).willReturn(true)
        given(artistCall.execute()).willReturn(artistResponse)
        given(remoteDataSource.fetchArtist("tst", 1)).willReturn(artistCall)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeEqualTo Result.Success(artistDataTest)
        Mockito.verify(remoteDataSource).fetchArtist("tst", 1)
    }

    @Test
    fun `getArtist should return network failure when no connection`() {
        given { networkHandler.isConnected() }.willReturn(false)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeInstanceOf Result.Failure::class.java
        artist.isFailure shouldBeEqualTo true
        artist.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(remoteDataSource)
    }

    @Test
    fun `getArtist should return network failure when undefined connection`() {
        given { networkHandler.isConnected() }.willReturn(null)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeInstanceOf Result.Failure::class.java
        artist.isFailure shouldBeEqualTo true
        artist.fold(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(remoteDataSource)
    }

    @Test
    fun `getArtist should return server error if no successful response`() {
        given { networkHandler.isConnected() }.willReturn(true)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeInstanceOf Result.Failure::class.java
        artist.isFailure shouldBeEqualTo true
        artist.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
        verify(remoteDataSource).fetchArtist("tst", 1)
    }

    @Test
    fun `getArtist should catch exception`() {
        given { networkHandler.isConnected() }.willReturn(true)

        val artist = repository.getArtist("tst", 1)

        artist shouldBeInstanceOf Result.Failure::class.java
        artist.isFailure shouldBeEqualTo true
        artist.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
        verify(remoteDataSource).fetchArtist("tst", 1)
    }

    @Test
    fun `getAlbumDetail should return empty list when server response is null`() {
        given { networkHandler.isConnected() }.willReturn(true)
        given(albumDetailResponse.body()).willReturn(null)
        given(albumDetailResponse.isSuccessful).willReturn(true)
        given(albumDetailCall.execute()).willReturn(albumDetailResponse)
        given(remoteDataSource.fetchAlbumDetail("tst", "tst")).willReturn(albumDetailCall)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeEqualTo Result.Success(AlbumDetailResponse.empty().toTopAlbumDetailView())
        Mockito.verify(remoteDataSource).fetchAlbumDetail("tst", "tst")
    }

    @Test
    fun `getAlbumDetail should return artists list from server`() {
        given { networkHandler.isConnected() }.willReturn(true)
        given { albumDetailResponse.body() }.willReturn(albumDetailResponseTest)
        given(albumDetailResponse.isSuccessful).willReturn(true)
        given(albumDetailCall.execute()).willReturn(albumDetailResponse)
        given(remoteDataSource.fetchAlbumDetail("tst", "tst")).willReturn(albumDetailCall)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeEqualTo Result.Success(albumDetailResponseTest.toTopAlbumDetailView())
        Mockito.verify(remoteDataSource).fetchAlbumDetail("tst", "tst")
    }

    @Test
    fun `getAlbumDetail should return network failure when no connection`() {
        given { networkHandler.isConnected() }.willReturn(false)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeInstanceOf Result.Failure::class.java
        albumDetail.isFailure shouldBeEqualTo true
        albumDetail.fold(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(remoteDataSource)
    }

    @Test
    fun `getAlbumDetail should return network failure when undefined connection`() {
        given { networkHandler.isConnected() }.willReturn(null)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeInstanceOf Result.Failure::class.java
        albumDetail.isFailure shouldBeEqualTo true
        albumDetail.fold(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verifyZeroInteractions(remoteDataSource)
    }

    @Test
    fun `getAlbumDetail should return server error if no successful response`() {
        given { networkHandler.isConnected() }.willReturn(true)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeInstanceOf Result.Failure::class.java
        albumDetail.isFailure shouldBeEqualTo true
        albumDetail.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
        verify(remoteDataSource).fetchAlbumDetail("tst", "tst")
    }

    @Test
    fun `getAlbumDetail should catch exception`() {
        given { networkHandler.isConnected() }.willReturn(true)

        val albumDetail = repository.getAlbumDetail("tst", "tst")

        albumDetail shouldBeInstanceOf Result.Failure::class.java
        albumDetail.isFailure shouldBeEqualTo true
        albumDetail.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
        verify(remoteDataSource).fetchAlbumDetail("tst", "tst")
    }
}