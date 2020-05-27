package com.matinfard.musicmanagement.core.extention

import android.view.View
import com.matinfard.musicmanagement.core.platform.BaseFragment
import com.matinfard.musicmanagement.core.platform.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

val BaseFragment.viewContainer: View get() = (activity as MainActivity).fragmentContainer

