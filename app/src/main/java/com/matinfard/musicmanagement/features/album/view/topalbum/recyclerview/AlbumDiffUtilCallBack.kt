package com.matinfard.musicmanagement.features.album.view.topalbum.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView

class AlbumDiffUtilCallBack(
    oldList: List<TopAlbumView>,
    newList: List<TopAlbumView>
) : DiffUtil.Callback() {

    private val mOldList: List<TopAlbumView> = oldList
    private val mNewList: List<TopAlbumView> = newList

    override fun getOldListSize(): Int =
        mOldList.size

    override fun getNewListSize(): Int =
        mNewList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean =
        oldItemPosition == newItemPosition

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean =
        (mOldList[oldItemPosition].artistName == mNewList[newItemPosition].artistName)
                && (mOldList[oldItemPosition].albumName == mNewList[newItemPosition].albumName)

}


