package co.shepherd.sports.dao

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.db.SportsDatabase
import co.shepherd.sports.db.dao.ScheduleDao
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
class ScheduleDaoShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sportsDatabase: SportsDatabase
    private lateinit var scheduleDao: ScheduleDao

    @Before
    fun setUp() {
        sportsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SportsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        scheduleDao = sportsDatabase.scheduleDao()
    }

    @After
    fun closeDatabase() {
        sportsDatabase.close()
    }

    @Test
    fun `empty database count must be zero`() {
        // When
        val count = scheduleDao.getCount()

        // Then
        Truth.assertThat(count).isEqualTo(0)
    }

    @Test
    fun `insert one entity and count must be one`() {
        // When
        scheduleDao.insertSchedule(generateScheduleEntity())

        // Then
        val count = scheduleDao.getCount()
        Truth.assertThat(count).isEqualTo(1)
    }

    @Test
    fun `insert one entity and test get function`() {
        // When
        scheduleDao.insertSchedule(generateScheduleEntity())

        // Then
        val entity = scheduleDao.getSchedule().getOrAwaitValue()
        Truth.assertThat(entity.id).isEqualTo("1")
    }

    @Test
    fun `delete and insert a schedule`() {
        // When
        scheduleDao.deleteAndInsert(generateScheduleEntity())
        val count = scheduleDao.getCount()
        Truth.assertThat(count).isEqualTo(1)

        // Then
        scheduleDao.deleteAndInsert(generateScheduleEntity())
        val newCount = scheduleDao.getCount()
        val value = scheduleDao.getSchedule().getOrAwaitValue()
        Truth.assertThat(newCount).isEqualTo(1)
        Truth.assertThat(value.id).isEqualTo(1)
    }

    @Test
    fun `first insert a schedule then delete and count must be zero`() {
        // When
        scheduleDao.deleteAndInsert(generateScheduleEntity())
        val count = scheduleDao.getCount()
        Truth.assertThat(count).isEqualTo(1)

        // Then
        scheduleDao.deleteAll()
        val newCount = scheduleDao.getCount()
        Truth.assertThat(newCount).isEqualTo(0)
    }
}