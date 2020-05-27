package com.matinfard.musicmanagement.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matinfard.musicmanagement.R
import com.matinfard.musicmanagement.features.album.view.myalbum.MyAlbumFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class MyAlbumFragmentTest {

        @Test
        fun header_items_are_showing() {
            launchFragmentInContainer<MyAlbumFragment>()
            onView(withId(R.id.searchButtonIV)).check(matches(isDisplayed()))
            onView(withId(R.id.appNameTV)).check(matches(isDisplayed()))
            onView(withId(R.id.myAlbumHeaderTV)).check(matches(isDisplayed()))
        }

        @Test
        fun click_on_search_button_will_redirect_to_search_fragment() {
            val mockNavController = mock(NavController::class.java)

            launchFragmentInContainer {
                MyAlbumFragment()
                    .also { fragment ->
                        fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                            if (viewLifecycleOwner != null) {
                                Navigation.setViewNavController(
                                    fragment.requireView(),
                                    mockNavController) } } } }

             onView(withId(R.id.searchButtonIV)).perform(click())

             verify(mockNavController).navigate(R.id.action_albumFragment_to_searchArtistFragment)
        }
    }