package co.shepherd.sports.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.shepherd.sports.domain.usecase.ScheduleUseCase
import co.shepherd.sports.ui.schedule.ScheduleViewModel
import co.shepherd.sports.ui.schedule.ScheduleViewState
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
class ScheduleViewModelShould {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var scheduleUseCase: ScheduleUseCase

    private lateinit var scheduleViewModel: ScheduleViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        scheduleViewModel = ScheduleViewModel(
            scheduleUseCase
        )
    }

    @Test
    fun `given loading state, when setScheduleParams called, then update live data for loading status`() {
        // Given
        val viewStateObserver: Observer<ScheduleViewState> = mockk(relaxUnitFun = true)
        scheduleViewModel.getScheduleViewState().observeForever(viewStateObserver)

        val viewStateLiveData: MutableLiveData<ScheduleViewState> = MutableLiveData()
        viewStateLiveData.postValue(ScheduleViewState(Status.LOADING, null, null))

        // When
        every { scheduleUseCase.execute(any()) } returns viewStateLiveData
        scheduleViewModel.setScheduleParams(
            ScheduleUseCase.ScheduleParams(true)
        )

        // Then
        val scheduleViewStateSlots = mutableListOf<ScheduleViewState>()
        verify { viewStateObserver.onChanged(capture(scheduleViewStateSlots)) }

        val loadingState = scheduleViewStateSlots[0]
        Truth.assertThat(loadingState.status).isEqualTo(Status.LOADING)
    }
}