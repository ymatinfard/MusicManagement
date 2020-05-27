package com.matinfard.musicmanagement.features.album.view.topalbum

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.platform.BaseFragment
import com.matinfard.musicmanagement.core.platform.PARAM_ALBUM
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumView
import com.matinfard.musicmanagement.features.album.model.topalbum.api.TopAlbumView
import com.matinfard.musicmanagement.features.album.view.topalbum.recyclerview.TopAlbumAdapter
import kotlinx.android.synthetic.main.fragment_top_album.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class TopAlbumFragment : BaseFragment() {

    private val mViewModel: TopAlbumViewModel by viewModel()
    private val topAlbumAdapter: TopAlbumAdapter by inject()

    override fun layoutId(): Int = R.layout.fragment_top_album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistName = arguments!![getString(R.string.artist_name)].toString()

        setObservers()
        initRecyclerView()

        mViewModel.loadAlbum(artistName)
    }

    private fun setObservers() {
        with(mViewModel) {
            topAlbumList.observe(viewLifecycleOwner, ::renderTopAlbumList)
            isLoading.observe(viewLifecycleOwner, ::handleProgressbar)
            failure.observe(viewLifecycleOwner, ::handleFailure)

        }
    }

    private fun renderTopAlbumList(topAlbums: List<TopAlbumView>) {
        topAlbumAdapter.submitList(topAlbums)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        topAlbumAdapter.clickListener = onListItemClicked

        with(topAlbumRV) {
            adapter = topAlbumAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    if (totalItemCount == linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
                        loadMoreItems()
                }
            })
        }
    }

    private val onListItemClicked: (String, String) -> Unit = { artistName, albumName ->
        val arguments = Bundle()
        arguments.putParcelable(PARAM_ALBUM,
            AlbumView(
                artistName,
                albumName,
                ViewMode.ONLINE
            )
        )
        findNavController().navigate(
            R.id.action_topAlbumFragment_to_topAlbumDetailFragment,
            arguments
        )
    }

    private fun loadMoreItems() {
        mViewModel.loadMoreItems()
    }

}