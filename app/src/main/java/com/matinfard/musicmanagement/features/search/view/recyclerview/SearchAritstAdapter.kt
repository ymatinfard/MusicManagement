package com.matinfard.musicmanagement.features.search.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.extention.loadFromUrl
import com.matinfard.musicmanagement.core.extention.shortenTo
import com.matinfard.musicmanagement.core.platform.MAX_TEXT_LEN
import com.matinfard.musicmanagement.features.search.model.api.ArtistView
import kotlinx.android.synthetic.main.artist_item_layout.view.*

class SearchAritstAdapter : RecyclerView.Adapter<SearchAritstAdapter.ViewHolder>() {
    private var artistList: MutableList<ArtistView> = mutableListOf()
    var clickListener : (String) -> Unit = { _ ->}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.artist_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artistObject = artistList[position]
        holder.bind(artistObject.name, artistObject.image[0].url, clickListener)
    }

    fun submitList(newArtistList: List<ArtistView>) {
        val diffCallback = ArtistDiffUtilCallBack(artistList, newArtistList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        artistList.clear()
        artistList.addAll(newArtistList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val artistNameTV: TextView = itemView.artistNameTV
        private val artistImageIV: ImageView = itemView.artistPhotoIV

        fun bind(artistName: String, artistImageUrl: String, listItemClickListener : (String) -> Unit) {
            artistNameTV.text = artistName.shortenTo(MAX_TEXT_LEN)
            artistImageIV.loadFromUrl(artistImageUrl)
            itemView.artistCL.setOnClickListener{
                listItemClickListener(artistName)
            }
        }
    }
}

