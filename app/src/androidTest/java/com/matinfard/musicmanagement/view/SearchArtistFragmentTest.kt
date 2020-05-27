package com.matinfard.musicmanagement.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.features.search.view.SearchArtistFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchArtistFragmentTest {

    @Test
    fun search_artist_elements_are_showing(){

        launchFragmentInContainer<SearchArtistFragment>()

        onView(withId(R.id.backButtonIV)).check(matches(isDisplayed()))
        onView(withId(R.id.artistSearchET)).check(matches(isDisplayed()))
        onView(withId(R.id.searchButtonIV)).check(matches(isDisplayed()))
        onView(withId(R.id.searchIconSmallIV)).check(matches(isDisplayed()))

    }
}