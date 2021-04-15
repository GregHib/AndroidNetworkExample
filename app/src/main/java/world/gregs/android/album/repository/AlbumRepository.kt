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

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import world.gregs.android.album.api.ResourceApi
import world.gregs.android.album.db.ResourceDb

/**
 * Repository implementation that uses a database backed [androidx.paging.PagingSource] and
 * [androidx.paging.RemoteMediator] to load pages from network when there are no more items cached
 * in the database to load.
 */
class AlbumRepository(val db: ResourceDb, val api: ResourceApi) {

    @OptIn(ExperimentalPagingApi::class)
    fun resourcesOfSize(pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageRemoteMediator(db, api)
    ) {
        db.albums().getAllAscending()
    }.flow
}
