package world.gregs.android.album

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import world.gregs.android.album.db.AlbumDao
import world.gregs.android.album.db.ResourceDb
import world.gregs.android.album.model.Album
import world.gregs.android.album.repository.AlbumFactory
import world.gregs.android.album.repository.FakeResourceApi
import world.gregs.android.album.repository.PageRemoteMediator

@OptIn(ExperimentalCoroutinesApi::class)
class PageRemoteMediatorTest {
    private val albumFactory = AlbumFactory()
    private val fakeAlbums = listOf(
            albumFactory.createAlbum(1),
            albumFactory.createAlbum(1),
            albumFactory.createAlbum(1)
    )
    private val fakeApi = FakeResourceApi().apply {
        fakeAlbums.forEach { album -> addAlbum(album) }
    }

    @RelaxedMockK
    private lateinit var database: ResourceDb

    @RelaxedMockK
    private lateinit var dao: AlbumDao

    @RelaxedMockK
    private lateinit var pagingState: PagingState<Int, Album>

    private lateinit var mediator: PageRemoteMediator

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic("androidx.room.RoomDatabaseKt")
        every { database.albums() } returns dao
        // Invoke immediately
        coEvery { database.withTransaction(any<suspend () -> Unit>()) } coAnswers {
            val block: suspend () -> Unit = arg(1)
            block.invoke()
        }
        mediator = PageRemoteMediator(
                database,
                fakeApi
        )
    }

    @Test
    fun `Save albums to database`() = runBlockingTest {
        mediator.load(LoadType.APPEND, pagingState)

        coVerify {
            dao.insertAll(fakeAlbums)
        }
    }

    @Test
    fun `Clear database albums on refresh`() = runBlockingTest {
        mediator.load(LoadType.REFRESH, pagingState)

        coVerifyOrder {
            dao.deleteAll()
            dao.insertAll(fakeAlbums)
        }
    }
}