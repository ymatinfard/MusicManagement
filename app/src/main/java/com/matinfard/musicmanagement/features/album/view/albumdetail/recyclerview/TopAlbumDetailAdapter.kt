package com.matinfard.musicmanagement.features.album.view.albumdetail.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.extention.inflate
import com.matinfard.musicmanagement.core.extention.shortenTo
import com.matinfard.musicmanagement.core.platform.MAX_TEXT_LEN
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailTrack
import kotlinx.android.synthetic.main.top_album_track_layout.view.*

class TopAlbumDetailAdapter : RecyclerView.Adapter<TopAlbumDetailAdapter.ViewHolder>() {
    private var topAlbumTrackList: List<AlbumDetailTrack> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.top_album_track_layout))
    }

    override fun getItemCount(): Int = topAlbumTrackList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val albumTrackObject = topAlbumTrackList[position]
        holder.bind(albumTrackObject.name, position)
    }

    fun submitList(topAlbums: List<AlbumDetailTrack>) {
        topAlbumTrackList = topAlbums
        notifyDataSetChanged()
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trackNumTV: TextView = itemView.trackNumTV
        private val trackNameTV: TextView = itemView.trackNameTV

        fun bind(
            trackName: String,
            position: Int
        ) {
            trackNameTV.text = trackName.shortenTo(MAX_TEXT_LEN)
            trackNumTV.text = (position + 1).toString()
        }
    }
}

