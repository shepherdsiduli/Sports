package co.shepherd.sports.dao

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.db.SportsDatabase
import co.shepherd.sports.db.dao.EventsDao
import co.shepherd.sports.db.dao.ScheduleDao
import co.shepherd.sports.utils.generateEventsEntity
import co.shepherd.sports.utils.generateScheduleEntity
import co.shepherd.sports.utils.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class EventsDaoShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sportsDatabase: SportsDatabase
    private lateinit var eventsDao: EventsDao

    @Before
    fun setUp() {
        sportsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SportsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        eventsDao = sportsDatabase.eventsDao()
    }

    @After
    fun closeDatabase() {
        sportsDatabase.close()
    }

    @Test
    fun `empty database count must be zero`() {
        // When
        val count = eventsDao.getCount()

        // Then
        Truth.assertThat(count).isEqualTo(0)
    }

    @Test
    fun `insert one entity and test get function`() {
        // When
        eventsDao.insertEvents(generateEventsEntity())

        // Then
        val entity = eventsDao.getEvents().getOrAwaitValue()
        Truth.assertThat(entity.id).isEqualTo(1)
    }
}