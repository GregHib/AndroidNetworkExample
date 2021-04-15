package world.gregs.android.album.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import world.gregs.android.album.api.ResourceApi
import world.gregs.android.album.db.ResourceDb
import world.gregs.android.album.db.AlbumDao
import world.gregs.android.album.model.Album
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageRemoteMediator(
        private val db: ResourceDb,
        private val api: ResourceApi
) : RemoteMediator<Int, Album>() {
    private val albumDao: AlbumDao = db.albums()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
            loadType: LoadType, 
            state: PagingState<Int, Album>
    ): MediatorResult {
        return try {
            val items = api.getAlbums().toList()
            db.withTransaction {
                if (loadType == REFRESH) {
                    albumDao.deleteAll()
                }

                albumDao.insertAll(items)
            }
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
