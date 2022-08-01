package co.shepherd.sports.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.domain.datasources.events.EventsLocalDataSource
import co.shepherd.sports.domain.datasources.events.EventsRemoteDataSource
import co.shepherd.sports.utils.createSampleEventsResponse
import co.shepherd.sports.utils.domain.Resource
import co.shepherd.sports.utils.domain.Status
import co.shepherd.sports.utils.generateEventsEntity
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
class EventsRepositoryShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var eventsRemoteDataSource: EventsRemoteDataSource

    @MockK
    lateinit var eventsLocalDataSource: EventsLocalDataSource

    private lateinit var eventsRepository: EventsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        eventsRepository = EventsRepository(
            eventsRemoteDataSource,
            eventsLocalDataSource
        )
    }

    @Test
    fun `given fetchRequired = false, when getEvents called, then make sure db called`() {
        // Given
        val fetchRequired = false
        val eventsLiveData: MutableLiveData<EventsEntity> = MutableLiveData()
        eventsLiveData.postValue(generateEventsEntity())
        val mockedObserver: Observer<Resource<EventsEntity>> = mockk(relaxUnitFun = true)

        // When
        every { eventsLocalDataSource.getEvents() } returns eventsLiveData
        every {
            eventsRemoteDataSource.getEvents()
        } returns
                Single.just(createSampleEventsResponse())

        eventsRepository
            .loadEvents(
                fetchRequired
            )
            .observeForever(mockedObserver)

        /**
         * shouldFetch == false -> loadFromDb()
         */

        // Make sure network wasn't called
        verify {
            eventsRemoteDataSource.getEvents(
            ) wasNot called
        }
        // Make sure db called
        verify { eventsLocalDataSource.getEvents() }

        // Then
        val eventsEntitySlots = mutableListOf<Resource<EventsEntity>>()
        verify { mockedObserver.onChanged(capture(eventsEntitySlots)) }

        val eventsEntity = eventsEntitySlots[0]
        Truth.assertThat(eventsEntity.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(eventsEntity.data?.id).isEqualTo(1)
    }
}