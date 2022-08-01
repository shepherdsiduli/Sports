package co.shepherd.sports.ui.events

import androidx.databinding.ObservableField
import co.shepherd.sports.core.BaseViewModel
import co.shepherd.sports.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel  @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<Event>()
}