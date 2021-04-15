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

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import world.gregs.android.album.model.Album

/**
 * A simple adapter implementation that shows albums.
 */
class AlbumViewAdapter : PagingDataAdapter<Album, AlbumViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
            holder: AlbumViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.create(parent)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
                    oldItem.id == newItem.id
        }
    }
}
