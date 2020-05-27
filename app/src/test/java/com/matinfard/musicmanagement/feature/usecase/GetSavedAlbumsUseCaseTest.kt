package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.core.usecase.UseCase
import com.matinfard.musicmanagement.features.album.model.topalbum.api.*
import com.matinfard.musicmanagement.features.album.usecase.GetSavedAlbumsUseCase
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
class GetSavedAlbumsUseCaseTest{

    private lateinit var getSavedAlbumsUseCase: GetSavedAlbumsUseCase

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup(){
        getSavedAlbumsUseCase = GetSavedAlbumsUseCase(repository)
    }

    @Test
    fun `saveAlbumDetailUseCase should return album id after saving album info`(){
        val topAlbumResponseTest =
            TopAlbumResponse(
                TopAlbums(
                    listOf(
                        TopAlbum(
                            "tst",
                            TopAlbumArtist(
                                "tst"
                            ), listOf(
                                TopAlbumImage(
                                    "url"
                                )
                            )
                        )
                    ),
                    TopAlbumAttr(
                        "tst",
                        1,
                        20,
                        200,
                        2000
                    )
                )
            )

        given { repository.getSavedAlbums() }.willReturn(Result.Success(topAlbumResponseTest.toTopAlbumsData().albums))

        runBlocking { getSavedAlbumsUseCase.run(UseCase.None())}

        verify(repository).getSavedAlbums()
        verifyNoMoreInteractions(repository)
    }


}