package com.matinfard.musicmanagement.features.album.view.topalbum.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.extention.inflate
import com.matinfard.musicmanagement.core.extention.loadFromUrl
import com.matinfard.musicmanagement.core.extention.shortenTo
import com.matinfard.musicmanagement.core.platform.MAX_TEXT_LEN
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import kotlinx.android.synthetic.main.artist_item_layout.view.artistNameTV
import kotlinx.android.synthetic.main.top_album_item_layout.view.*

class TopAlbumAdapter : RecyclerView.Adapter<TopAlbumAdapter.ViewHolder>() {
    private var topAlbumList: MutableList<TopAlbumView> = mutableListOf()
    var clickListener: (String, String) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.top_album_item_layout))
    }

    override fun getItemCount(): Int = topAlbumList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topAlbumObject = topAlbumList[position]
        holder.bind(
            topAlbumObject.artistName,
            topAlbumObject.albumName,
            topAlbumObject.albumImage,
            clickListener
        )
    }

    fun submitList(newTopAlbumList: List<TopAlbumView>) {

        val diffCallback = AlbumDiffUtilCallBack(topAlbumList, newTopAlbumList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        topAlbumList.clear()
        topAlbumList.addAll(newTopAlbumList)
        diffResult.dispatchUpdatesTo(this)
    }


     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val artistNameTV: TextView = itemView.artistNameTV
        private val topAlbumNameTV: TextView = itemView.albumNameTV
        private val topAlbumIV: ImageView = itemView.topAlbumImageIV

        fun bind(
            artistName: String,
            topAlbumName: String,
            topAlbumImageUrl: String,
            listItemClickListener: (String, String) -> Unit
        ) {
            artistNameTV.text = artistName.shortenTo(MAX_TEXT_LEN)
            topAlbumNameTV.text = topAlbumName.shortenTo(MAX_TEXT_LEN)
            topAlbumIV.loadFromUrl(topAlbumImageUrl)
            itemView.topAlbumCL.setOnClickListener {
                listItemClickListener(artistName, topAlbumName)

            }
        }
    }
}

