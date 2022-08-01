package co.shepherd.sports.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.domain.usecase.EventsUseCase
import co.shepherd.sports.ui.events.EventsViewModel
import co.shepherd.sports.ui.events.EventsViewState
import co.shepherd.sports.utils.domain.Status
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class EventsViewModelShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var eventsUseCase: EventsUseCase

    private lateinit var eventsViewModel: EventsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        eventsViewModel = EventsViewModel(
            eventsUseCase
        )
    }

    @Test
    fun `given loading state, when setEventsParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<EventsViewState> = mockk(relaxUnitFun = true)
        eventsViewModel.getEventsViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<EventsViewState> = MutableLiveData()
        viewStateLiveData.postValue(EventsViewState(Status.LOADING, null, null))

        // When
        every { eventsUseCase.execute(any()) } returns viewStateLiveData
        eventsViewModel.setEventsParams(
            EventsUseCase.EventsParams(true)
        )

        // Then
        val eventsViewStateSlots = mutableListOf<EventsViewState>()
        verify { viewStateObserver.onChanged(capture(eventsViewStateSlots)) }

        val loadingState = eventsViewStateSlots[0]
        Truth.assertThat(loadingState.status).isEqualTo(Status.LOADING)
    }
}