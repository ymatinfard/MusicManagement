package com.matinfard.musicmanagement.features.search.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.extention.hideKeyboard
import com.matinfard.musicmanagement.core.extention.showKeyboard
import com.matinfard.musicmanagement.core.platform.BaseFragment
import com.matinfard.musicmanagement.features.search.model.api.ArtistView
import com.matinfard.musicmanagement.features.search.view.recyclerview.SearchAritstAdapter
import kotlinx.android.synthetic.main.fragment_artist_search.*
import kotlinx.android.synthetic.main.search_view_layout.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class SearchArtistFragment : BaseFragment() {

    private val mViewModel: SearchArtistViewModel by viewModel()
    private val artistAdapter: SearchAritstAdapter by inject()

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun layoutId(): Int = R.layout.fragment_artist_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistSearchET.showKeyboard()
        linearLayoutManager = LinearLayoutManager(context)

        setObservers()
        initRecyclerView()
        setOnClicks()
    }

    private fun setOnClicks() {
        searchButtonIV.setOnClickListener {
            val searchValue = artistSearchET.text.trim().toString()
            if (searchValue.isNotEmpty()) {
                mViewModel.searchArtist(searchValue)
                linearLayoutManager.scrollToPositionWithOffset(0, 0)
            } else
                notify(R.string.empty_search_value)
        }

        backButtonIV.setOnClickListener { back ->
            back.hideKeyboard()
            requireActivity().onBackPressed()
        }

        artistSearchET.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN)
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        searchButtonIV.callOnClick()
                        true
                    }
                    else -> false
                }
            false
        }
    }

    private fun setObservers() {
        with(mViewModel) {
            artistList.observe(viewLifecycleOwner, ::renderArtistList)
            isLoading.observe(viewLifecycleOwner, ::handleProgressbar)
            failure.observe(viewLifecycleOwner, ::handleFailure)
        }
    }

    private fun initRecyclerView() {
        artistAdapter.clickListener = onListItemClicked

        with(artistRV) {
            adapter = artistAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    if (totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1)
                        loadMoreItems()
                }
            })
        }
    }

    private fun renderArtistList(artists: List<ArtistView>) {
        artistAdapter.submitList(artists)
        artistSearchET.hideKeyboard()
    }

    private val onListItemClicked: (String) -> Unit = { artistName ->
        artistSearchET.hideKeyboard()
        findNavController().navigate(
            R.id.action_searchArtistFragment_to_topAlbumFragment
            , bundleOf(getString(R.string.artist_name) to artistName)
        )
    }

    private fun loadMoreItems() {
        mViewModel.loadMoreItems()
    }

}