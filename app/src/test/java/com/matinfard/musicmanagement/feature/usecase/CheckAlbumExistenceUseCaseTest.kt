package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.usecase.CheckAlbumExistenceUseCase
import com.matinfard.musicmanagement.features.album.usecase.CheckAlbumExistenceUseCase.Params
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
class CheckAlbumExistenceUseCaseTest{

    private lateinit var checkAlbumExistenceUseCase: CheckAlbumExistenceUseCase

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup(){
        checkAlbumExistenceUseCase = CheckAlbumExistenceUseCase(repository)
    }

    @Test
    fun `checkAlbumExistenceUseCase should return album id if album exists`(){
        given { repository.checkAlbumExistence(
            ArtistEntity(),
            AlbumEntity()
        ) }.willReturn(
            Result.Success("123"))

        runBlocking { checkAlbumExistenceUseCase.run(Params(
            ArtistEntity(),
            AlbumEntity()
        ))}

        verify(repository).checkAlbumExistence(
            ArtistEntity(),
            AlbumEntity()
        )
        verifyNoMoreInteractions(repository)
    }


}