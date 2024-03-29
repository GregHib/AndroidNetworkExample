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

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.example.lib.R
import world.gregs.android.album.model.Album

/**
 * A RecyclerView ViewHolder that displays an album title.
 */
class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private var post : Album? = null

    fun bind(album: Album?) {
        this.post = album
        title.text = album?.title ?: "loading"
    }

    companion object {
        fun create(parent: ViewGroup): AlbumViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_item, parent, false)
            return AlbumViewHolder(view)
        }
    }
}