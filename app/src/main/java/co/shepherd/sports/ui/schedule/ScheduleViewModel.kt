package co.shepherd.sports.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import co.shepherd.sports.core.BaseViewModel
import co.shepherd.sports.domain.usecase.ScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject internal constructor(
    private val scheduleUseCase: ScheduleUseCase
) : BaseViewModel() {

    private val _scheduleParams: MutableLiveData<ScheduleUseCase.ScheduleParams> =
        MutableLiveData()

    fun getScheduleViewState() = scheduleViewState

    private val scheduleViewState: LiveData<ScheduleViewState> =
        _scheduleParams.switchMap { params ->
            scheduleUseCase.execute(params)
        }

    fun setScheduleParams(params: ScheduleUseCase.ScheduleParams) {
        if (_scheduleParams.value == params) {
            return
        }
        _scheduleParams.postValue(params)
    }

}