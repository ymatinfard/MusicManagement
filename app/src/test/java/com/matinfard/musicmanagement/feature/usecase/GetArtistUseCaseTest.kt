package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.search.model.api.ArtistImage
import com.matinfard.musicmanagement.features.search.model.api.ArtistView
import com.matinfard.musicmanagement.features.search.model.api.ArtistsData
import com.matinfard.musicmanagement.features.search.usecase.GetArtistUseCase
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
class GetArtistUseCaseTest {

    private lateinit var getArtistUseCase: GetArtistUseCase

    @Mock
    private lateinit var repository: Repository

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
    fun setup(){
        getArtistUseCase = GetArtistUseCase(repository)
    }

    @Test
    fun `getTopAlbumUseCase should return data from repository`(){
        given { repository.getArtist("tst", 1) }.willReturn(Result.Success(artistDataTest))

        runBlocking { getArtistUseCase.run(GetArtistUseCase.Params("tst", 1))}

        verify(repository).getArtist("tst", 1)
        verifyNoMoreInteractions(repository)
    }
}