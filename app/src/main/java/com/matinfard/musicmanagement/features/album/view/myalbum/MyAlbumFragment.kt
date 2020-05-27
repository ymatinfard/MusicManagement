package com.matinfard.musicmanagement.features.album.view.myalbum

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.platform.BaseFragment
import com.matinfard.musicmanagement.core.platform.PARAM_ALBUM
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumView
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.view.topalbum.recyclerview.TopAlbumAdapter
import kotlinx.android.synthetic.main.album_header_layout.*
import kotlinx.android.synthetic.main.fragment_my_album.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class MyAlbumFragment : BaseFragment() {

    private val mViewModel : MyAlbumViewModel by viewModel()
    private val myAlbumAdapter : TopAlbumAdapter by inject()

    override fun layoutId(): Int = R.layout.fragment_my_album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setOnClicks()
        initRecyclerView()

        if (firstTimeCreated(savedInstanceState))
            mViewModel.loadAlbums()
    }

    private fun setObservers() {
        with(mViewModel) {
            albumList.observe(viewLifecycleOwner, ::renderMyAlbumList)
            failure.observe(viewLifecycleOwner, ::handleFailure)
        }
    }

    private fun setOnClicks(){
        searchButtonIV.setOnClickListener {
            findNavController().navigate(R.id.action_albumFragment_to_searchArtistFragment)
        }
    }

    private fun renderMyAlbumList(list: List<TopAlbumView>){
        myAlbumAdapter.submitList(list)
    }

    private fun initRecyclerView() {
        myAlbumAdapter.clickListener = onListItemClicked
        with(myAlbumRV){
            layoutManager = LinearLayoutManager(context)
            adapter = myAlbumAdapter
        }
    }

    private val onListItemClicked: (String, String) -> Unit = { artistName, albumName ->
        val arguments = Bundle()
        arguments.putParcelable(PARAM_ALBUM,
            AlbumView(
                artistName,
                albumName,
                ViewMode.OFFLINE
            )
        )
        findNavController().navigate(R.id.action_albumFragment_to_topAlbumDetailFragment, arguments)
    }

}


