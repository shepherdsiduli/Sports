package co.shepherd.sports.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import co.shepherd.sports.core.BaseViewModel
import co.shepherd.sports.domain.usecase.EventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class EventsViewModel @Inject internal constructor(
    private val eventsUseCase: EventsUseCase
) : BaseViewModel() {

    private val _eventsParams: MutableLiveData<EventsUseCase.EventsParams> =
        MutableLiveData()

    fun getEventsViewState() = eventsViewState

    private val eventsViewState: LiveData<EventsViewState> =
        _eventsParams.switchMap { params ->
            eventsUseCase.execute(params)
        }

    fun setEventsParams(params: EventsUseCase.EventsParams) {
        if (_eventsParams.value == params) {
            return
        }
        _eventsParams.postValue(params)
    }
}