package com.matinfard.musicmanagement.features.album.model.albumdetail.api

import android.os.Parcel
import android.os.Parcelable
import com.matinfard.musicmanagement.core.platform.KParcelable
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.core.platform.readEnum
import com.matinfard.musicmanagement.core.platform.writeEnum

data class AlbumView(val artistName: String, val albumName: String, val viewMode: ViewMode): KParcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readEnum<ViewMode>()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest){
            writeString(artistName)
            writeString(albumName)
            writeEnum(viewMode)
        }
    }

    companion object CREATOR : Parcelable.Creator<AlbumView> {
        override fun createFromParcel(parcel: Parcel): AlbumView {
            return AlbumView(
                parcel
            )
        }

        override fun newArray(size: Int): Array<AlbumView?> {
            return arrayOfNulls(size)
        }
    }
}