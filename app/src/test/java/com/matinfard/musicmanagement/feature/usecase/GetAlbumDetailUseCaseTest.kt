package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.*
import com.matinfard.musicmanagement.features.album.usecase.GetAlbumDetailUseCase
import com.matinfard.musicmanagement.features.album.usecase.GetAlbumDetailUseCase.Params
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAlbumDetailUseCaseTest {

    @Mock
    private lateinit var repository: Repository

    private lateinit var getAlbumDetailUseCase: GetAlbumDetailUseCase

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

    @Before
    fun setup(){
        getAlbumDetailUseCase = GetAlbumDetailUseCase(repository)
    }

    @Test
    fun `getAlbumDetailUseCase should return online data from repository when viewMode is online`(){
        given { repository.getAlbumDetail("tst", "tst")}.willReturn(Result.Success(albumDetailResponseTest.toTopAlbumDetailView()))

        runBlocking { getAlbumDetailUseCase.run(Params("tst", "tst", ViewMode.ONLINE)) }

        verify(repository).getAlbumDetail("tst", "tst")
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `getAlbumDetailUseCase should return offline(database) data from repository when viewMode is offline`(){
        given { repository.getSavedAlbumDetail("tst", "tst") }.willReturn(
            Result.Success(
            albumDetailResponseTest.toTopAlbumDetailView()))

        runBlocking { getAlbumDetailUseCase.run(Params("tst", "tst", ViewMode.OFFLINE)) }

        verify(repository).getSavedAlbumDetail("tst", "tst")
        verifyNoMoreInteractions(repository)
    }
}