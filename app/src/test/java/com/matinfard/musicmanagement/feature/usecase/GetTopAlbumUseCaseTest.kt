package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.album.model.topalbum.api.*
import com.matinfard.musicmanagement.features.album.usecase.GetTopAlbumUseCase
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
class GetTopAlbumUseCaseTest {

    private lateinit var getTopAlbumUseCase: GetTopAlbumUseCase

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup(){
        getTopAlbumUseCase = GetTopAlbumUseCase(repository)
    }

    @Test
    fun `getTopAlbumUseCase should return data from repository`(){
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

        given { repository.getArtistTopAlbum("tst", 1) }.willReturn(Result.Success(topAlbumResponseTest.toTopAlbumsData()))

        runBlocking { getTopAlbumUseCase.run(GetTopAlbumUseCase.Params("tst", 1))}

        verify(repository).getArtistTopAlbum("tst", 1)
        verifyNoMoreInteractions(repository)
    }
}