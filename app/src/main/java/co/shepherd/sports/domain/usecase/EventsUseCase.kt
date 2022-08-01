package co.shepherd.sports.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.repository.EventsRepository
import co.shepherd.sports.ui.events.EventsViewState
import co.shepherd.sports.utils.UseCaseLiveData
import co.shepherd.sports.utils.domain.Resource
import javax.inject.Inject

class EventsUseCase @Inject
internal constructor(
    private val repository: EventsRepository
) : UseCaseLiveData<EventsViewState, EventsUseCase.EventsParams, EventsRepository>() {

    override fun getRepository(): EventsRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: EventsParams?): LiveData<EventsViewState> {
        return repository.loadEvents(
            params?.fetchRequired
                ?: false
        ).map {
            onEventsResultReady(it)
        }
    }

    private fun onEventsResultReady(resource: Resource<EventsEntity>): EventsViewState {
        return EventsViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class EventsParams(
        val fetchRequired: Boolean
    ) : Params()
}