package co.shepherd.sports.ui.events

import co.shepherd.sports.core.BaseViewState
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.utils.domain.Status

class EventsViewState(
    val status: Status,
    val error: String? = null,
    val data: EventsEntity? = null
) : BaseViewState(status, error) {
    fun getEvents() = data
}