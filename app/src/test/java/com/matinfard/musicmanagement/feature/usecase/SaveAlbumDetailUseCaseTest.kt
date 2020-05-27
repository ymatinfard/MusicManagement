package com.matinfard.musicmanagement.feature.usecase

import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.platform.Result
import com.matinfard.musicmanagement.features.album.model.album.db.AlbumEntity
import com.matinfard.musicmanagement.features.album.model.albumdetail.db.AlbumTrackEntity
import com.matinfard.musicmanagement.features.search.model.db.ArtistEntity
import com.matinfard.musicmanagement.features.album.usecase.SaveAlbumDetailUseCase
import com.matinfard.musicmanagement.features.album.usecase.SaveAlbumDetailUseCase.Params
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
class SaveAlbumDetailUseCaseTest{

    private lateinit var saveAlbumDetailUseCase: SaveAlbumDetailUseCase

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setup(){
        saveAlbumDetailUseCase = SaveAlbumDetailUseCase(repository)

    }

    @Test
    fun `saveAlbumDetailUseCase should return album id after saving album info`(){
        given { repository.saveAlbumDetail(
            ArtistEntity(),
            AlbumEntity(), listOf(
                AlbumTrackEntity()
            )) }.willReturn(
            Result.Success("1234"))

        runBlocking { saveAlbumDetailUseCase.run(Params(
            ArtistEntity(),
            AlbumEntity(), listOf(
                AlbumTrackEntity()
            )))}

        verify(repository).saveAlbumDetail(
            ArtistEntity(),
            AlbumEntity(), listOf(
                AlbumTrackEntity()
            ))
        verifyNoMoreInteractions(repository)
    }


}