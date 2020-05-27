package com.matinfard.musicmanagement.features.album.model.topalbum.api

data class TopAlbumsData(val albums: List<TopAlbumView>, val pageNum : Int, val totalPage : Int )