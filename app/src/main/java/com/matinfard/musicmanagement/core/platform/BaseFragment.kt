package com.matinfard.musicmanagement.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.platform.Failure.*
import com.matinfard.musicmanagement.core.extention.viewContainer
import kotlinx.android.synthetic.main.progress_layout.*

abstract class  BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    private fun showProgress() = progressStatus(View.VISIBLE)

    private fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is MainActivity) this.progress.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun handleFailure(failure: Failure) {
        when (failure) {
            is NetworkConnection -> notify(R.string.failure_no_network_connection)
            is ServerError -> notify(R.string.failure_server_error)
            is UnknownError -> notify(R.string.failure_unknown_error)
        }
        hideProgress()
    }

    internal fun handleProgressbar(isLoading: Boolean){
        if (isLoading) showProgress() else hideProgress()
    }
}
