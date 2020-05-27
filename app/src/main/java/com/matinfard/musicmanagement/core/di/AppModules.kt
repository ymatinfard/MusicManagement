package com.matinfard.musicmanagement.core.di

import androidx.room.Room
import com.matinfard.musicmanagement.core.data.Repository
import com.matinfard.musicmanagement.core.data.RepositoryImpl
import com.matinfard.musicmanagement.core.data.local.AlbumDatabase
import com.matinfard.musicmanagement.core.data.local.LocalDataSource
import com.matinfard.musicmanagement.core.data.local.LocalDataSourceImpl
import com.matinfard.musicmanagement.core.data.remote.RemoteDataSource
import com.matinfard.musicmanagement.core.data.remote.RemoteDataSourceImpl
import com.matinfard.musicmanagement.core.platform.URL
import com.matinfard.musicmanagement.core.platform.NetworkHandler
import com.matinfard.musicmanagement.features.album.view.myalbum.MyAlbumViewModel
import com.matinfard.musicmanagement.features.album.usecase.GetSavedAlbumsUseCase
import com.matinfard.musicmanagement.features.album.usecase.*
import com.matinfard.musicmanagement.features.search.usecase.*
import com.matinfard.musicmanagement.features.search.view.SearchArtistViewModel
import com.matinfard.musicmanagement.features.search.view.recyclerview.SearchAritstAdapter
import com.matinfard.musicmanagement.features.album.view.topalbum.TopAlbumViewModel
import com.matinfard.musicmanagement.features.album.view.topalbum.recyclerview.TopAlbumAdapter
import com.matinfard.musicmanagement.features.album.view.albumdetail.AlbumDetailViewModel
import com.matinfard.musicmanagement.features.album.view.albumdetail.recyclerview.TopAlbumDetailAdapter
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module(override = true) {

    factory { RemoteDataSourceImpl() as RemoteDataSource }
    factory { LocalDataSourceImpl(get()) as LocalDataSource }
    factory { RepositoryImpl(get(), get(), get()) as Repository }
    factory { CheckAlbumExistenceUseCase(get()) }
    factory { SaveAlbumDetailUseCase(get()) }
    factory { GetTopAlbumUseCase(get()) }
    factory { GetAlbumDetailUseCase(get()) }
    factory { GetArtistUseCase(get()) }
    factory { GetSavedAlbumsUseCase(get()) }
    factory { DeleteAlbumUseCase(get()) }
    factory { SearchAritstAdapter() }
    factory { TopAlbumAdapter() }
    factory { TopAlbumDetailAdapter() }
    factory { NetworkHandler(androidContext()) }

    viewModel { AlbumDetailViewModel(get(), get(), get(), get()) }
    viewModel { SearchArtistViewModel(get()) }
    viewModel { TopAlbumViewModel(get()) }
    viewModel { MyAlbumViewModel(get()) }

    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            AlbumDatabase::class.java,
            "album_database").build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}