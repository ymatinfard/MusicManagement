package com.matinfard.musicmanagement.features.album.view.albumdetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.extention.loadFromUrl
import com.matinfard.musicmanagement.core.extention.shortenTo
import com.matinfard.musicmanagement.core.platform.BaseFragment
import com.matinfard.musicmanagement.core.platform.PARAM_ALBUM
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumView
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumDetailView
import com.matinfard.musicmanagement.features.album.view.albumdetail.recyclerview.TopAlbumDetailAdapter
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumDetailFragment : BaseFragment() {

    private val mViewModel: AlbumDetailViewModel by viewModel()
    private val albumDetailAdapter: TopAlbumDetailAdapter by inject()
    private lateinit var argument: AlbumView
    private val maxTextSize = 25

    override fun layoutId(): Int {
        return R.layout.fragment_album_detail
    }

    private fun setOnclick() {
        albumSaveOrDeleteBTN.setOnClickListener {
            mViewModel.saveOrDeleteAlbum()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argument = arguments!![PARAM_ALBUM] as AlbumView

        if (firstTimeCreated(savedInstanceState))
            loadAlbumDetail()

        setObservers()
        setOnclick()
        initRecyclerView()
    }

    private fun setObservers() {
        with(mViewModel) {
            albumDetailList.observe(viewLifecycleOwner, ::renderAlbumDetailList)
            albumDeleteStatus.observe(viewLifecycleOwner, ::handleAlbumDelete)
            albumSaveStatus.observe(viewLifecycleOwner, ::handleAlbumSave)
            isAlbumExisting.observe(viewLifecycleOwner, ::handleAlbumExistence)
            failure.observe(viewLifecycleOwner, ::handleFailure)
        }
    }

    private fun renderAlbumDetailList(albumDetails: AlbumDetailView) {
        albumDetailAdapter.submitList(albumDetails.tracks)
        with(albumDetails) {
            albumDetailImageIV.loadFromUrl(imageUrl)
            albumDetailNameIV.text = albumName.shortenTo(maxTextSize)
            albumDetailArtistNameTV.text = artistName.shortenTo(maxTextSize)
        }
    }

    private fun handleAlbumDelete(isSuccessful: Boolean) {
        if (isSuccessful) {
            notify(R.string.delete_successful)
            if (argument.viewMode == ViewMode.OFFLINE)
                requireActivity().onBackPressed()
        } else
            notify(R.string.delete_failed)
    }

    private fun handleAlbumSave(isSuccessful: Boolean) {
        if (isSuccessful) {
            notify(R.string.save_successful)
        } else
            notify(R.string.save_failed)
    }

    private fun handleAlbumExistence(isExisting: Boolean) {
        albumSaveOrDeleteBTN.text =
            if (isExisting) getString(R.string.delete)
            else getString(R.string.save)
    }

    private fun initRecyclerView() {
        with(albumDetailRV) {
            adapter = albumDetailAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }
    }

    private fun loadAlbumDetail() {
        mViewModel.loadMovieDetails(
            argument.artistName,
            argument.albumName,
            argument.viewMode
        )
    }
}
