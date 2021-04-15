/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package world.gregs.android.album.ui

import android.app.Application
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import world.gregs.android.album.DefaultServiceLocator
import world.gregs.android.album.ServiceLocator
import world.gregs.android.album.api.ResourceApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import world.gregs.android.album.R
import world.gregs.android.album.repository.AlbumFactory
import world.gregs.android.album.repository.FakeResourceApi

/**
 * Simple sanity test to ensure data is displayed
 */
class MainActivityTest {

    private val postFactory = AlbumFactory()

    @Before
    fun init() {
        val fakeApi = FakeResourceApi()
        fakeApi.addAlbum(postFactory.createAlbum(1))
        fakeApi.addAlbum(postFactory.createAlbum(1))
        fakeApi.addAlbum(postFactory.createAlbum(1))
        val app = ApplicationProvider.getApplicationContext<Application>()
        // use a controlled service locator w/ fake API
        ServiceLocator.swap(
            object : DefaultServiceLocator(app = app, useInMemoryDb = true) {
                override fun getResourceApi(): ResourceApi = fakeApi
            }
        )
    }

    @Test
    fun showSomeResults() {
        ActivityScenario.launch<MainActivity>(
            Intent(ApplicationProvider.getApplicationContext(),
                    MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )

        onView(withId(R.id.list)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(3, recyclerView.adapter?.itemCount)
        }
    }
}