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

package world.gregs.android.album.repository

import world.gregs.android.album.api.ResourceApi
import world.gregs.android.album.model.Album
import java.io.IOException

/**
 * implements the ResourceApi with controllable requests
 */
class FakeResourceApi : ResourceApi {
    private val model = mutableListOf<Album>()
    var failureMsg: String? = null
    
    fun addAlbum(album: Album) {
        model.add(album)
    }

    override suspend fun getAlbums(): Array<Album> {
        failureMsg?.let {
            throw IOException(it)
        }
        return model.toTypedArray()
    }
}