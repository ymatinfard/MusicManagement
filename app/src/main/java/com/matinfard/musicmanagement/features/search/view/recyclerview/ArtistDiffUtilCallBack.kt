package com.matinfard.musicmanagement.features.search.view.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.matinfard.musicmanagement.features.search.model.api.ArtistView

class ArtistDiffUtilCallBack(
    oldList: List<ArtistView>,
    newList: List<ArtistView>
) : DiffUtil.Callback() {

    private val mOldList: List<ArtistView> = oldList
    private val mNewList: List<ArtistView> = newList

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
        mOldList[oldItemPosition].name == mNewList[newItemPosition].name

}


