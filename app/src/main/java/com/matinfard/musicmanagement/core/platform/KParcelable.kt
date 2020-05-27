package com.matinfard.musicmanagement.core.platform

import android.os.Parcel
import android.os.Parcelable

interface KParcelable : Parcelable {
    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel, flags: Int)
}

// Parcel extensions
inline fun <reified T : Enum<T>> Parcel.readEnum() = readInt().let { if (it >= 0) enumValues<T>()[it] else null }

inline fun <T : Enum<T>> Parcel.writeEnum(value: T?) = writeInt(value?.ordinal ?: -1)


