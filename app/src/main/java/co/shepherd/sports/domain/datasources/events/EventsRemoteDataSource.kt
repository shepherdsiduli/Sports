package co.shepherd.sports.domain.datasources.events

import co.shepherd.sports.domain.ApplicationAPI
import co.shepherd.sports.domain.model.Event
import io.reactivex.Single
import javax.inject.Inject

class EventsRemoteDataSource @Inject constructor(private val api: ApplicationAPI) {
    fun getEvents(): Single<List<Event>> = api.getEvents()
}