/*
 * Copyright 2019 The Android Open Source Project
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
package com.example.benchmark

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.benchmark.databinding.ActivityBenchmarkBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import world.gregs.android.album.model.Album
import world.gregs.android.album.ui.AlbumViewAdapter

class BenchmarkActivity : AppCompatActivity() {
    val testExecutor = TestExecutor()
    @VisibleForTesting
    lateinit var binding: ActivityBenchmarkBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBenchmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AlbumViewAdapter()
        binding.list.adapter = adapter

        val config = PagingConfig(
            pageSize = 5,
            initialLoadSize = 5
        )

        val pager = Pager(config, 0) {
            MockPagingSource()
        }

        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            pager.flow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}

class MockPagingSource : PagingSource<Int, Album>() {
    private fun generateAlbum(): Album {
        val title = List(10) { (0..100).random() }.joinToString("")
        return Album(title, "name", 1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        val key = params.key ?: 0
        return LoadResult.Page(List(200) { generateAlbum() }.toList(), key - 1, key + 1)
    }

    // Unused in benchmark.
    override fun getRefreshKey(state: PagingState<Int, Album>): Int? = null
}