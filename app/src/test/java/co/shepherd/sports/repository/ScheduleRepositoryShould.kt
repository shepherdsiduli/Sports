package co.shepherd.sports.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.domain.datasources.schedule.ScheduleLocalDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleRemoteDataSource
import co.shepherd.sports.utils.createSampleScheduleResponse
import co.shepherd.sports.utils.domain.Resource
import co.shepherd.sports.utils.domain.Status
import co.shepherd.sports.utils.generateScheduleEntity
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ScheduleRepositoryShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var scheduleRemoteDataSource: ScheduleRemoteDataSource

    @MockK
    lateinit var scheduleLocalDataSource: ScheduleLocalDataSource

    private lateinit var scheduleRepository: ScheduleRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        scheduleRepository = ScheduleRepository(
            scheduleRemoteDataSource,
            scheduleLocalDataSource
        )
    }

    @Test
    fun `given fetchRequired = false, when getSchedule called, then make sure db called`() {
        // Given
        val fetchRequired = false
        val scheduleLiveData: MutableLiveData<ScheduleEntity> = MutableLiveData()
        scheduleLiveData.postValue(generateScheduleEntity())
        val mockedObserver: Observer<Resource<ScheduleEntity>> = mockk(relaxUnitFun = true)

        // When
        every { scheduleLocalDataSource.getSchedule() } returns scheduleLiveData
        every {
            scheduleRemoteDataSource.getSchedule()
        } returns
                Single.just(createSampleScheduleResponse())

        scheduleRepository
            .loadSchedule(
                fetchRequired
            )
            .observeForever(mockedObserver)

        /**
         * shouldFetch == false -> loadFromDb()
         */

        // Make sure network wasn't called
        verify {
            scheduleRemoteDataSource.getSchedule(
            ) wasNot called
        }
        // Make sure db called
        verify { scheduleLocalDataSource.getSchedule() }

        // Then
        val scheduleEntitySlots = mutableListOf<Resource<ScheduleEntity>>()
        verify { mockedObserver.onChanged(capture(scheduleEntitySlots)) }

        val scheduleEntity = scheduleEntitySlots[0]
        Truth.assertThat(scheduleEntity.status).isEqualTo(Status.SUCCESS)
        //Truth.assertThat(scheduleEntity.data?.name).isEqualTo("Istanbul")
        Truth.assertThat(scheduleEntity.data?.id).isEqualTo(1)
    }

}