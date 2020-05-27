package com.matinfard.musicmanagement.view

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.core.platform.PARAM_ALBUM
import com.matinfard.musicmanagement.core.platform.ViewMode
import com.matinfard.musicmanagement.features.album.model.albumdetail.api.AlbumView
import com.matinfard.musicmanagement.features.album.view.albumdetail.AlbumDetailFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest {

    @Test
    fun album_detail_elements_are_showing(){

        val arguments = Bundle()
        arguments.putParcelable(PARAM_ALBUM,
            AlbumView(
                "artistName",
                "albumName",
                ViewMode.ONLINE
            )
        )
        launchFragmentInContainer<AlbumDetailFragment>(arguments)

        onView(withId(R.id.albumDetailArtistNameTV)).check(matches(isDisplayed()))
        onView(withId(R.id.albumDetailNameIV)).check(matches(isDisplayed()))
        onView(withId(R.id.albumDetailImageIV)).check(matches(isDisplayed()))
    }
}