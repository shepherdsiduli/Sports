package co.shepherd.sports.repository

import androidx.lifecycle.LiveData
import co.shepherd.sports.core.Constants.SportsNetworkService.RATE_LIMITER_TYPE
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.domain.datasources.events.EventsLocalDataSource
import co.shepherd.sports.domain.datasources.events.EventsRemoteDataSource
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.model.EventsResponse
import co.shepherd.sports.utils.domain.FrequencyLimiter
import co.shepherd.sports.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val eventsRemoteDataSource: EventsRemoteDataSource,
    private val eventsLocalDataSource: EventsLocalDataSource
) {
    private val eventsRateLimit = FrequencyLimiter<String>(30, TimeUnit.SECONDS)

    fun loadEvents(
        fetchRequired: Boolean
    ): LiveData<Resource<EventsEntity>> {
        return object : NetworkBoundResource<EventsEntity, List<Event>>() {
            override fun saveCallResponse(item: List<Event>) =
                eventsLocalDataSource.insertEvents(
                   // item
                EventsResponse(item)
                )

            override fun shouldFetch(data: EventsEntity?): Boolean = fetchRequired

            override fun loadFromDatabase(): LiveData<EventsEntity> =
                eventsLocalDataSource.getEvents()

            override fun createCall(): Single<List<Event>> =
                eventsRemoteDataSource.getEvents()

            override fun onFetchFailed() = eventsRateLimit.reset(RATE_LIMITER_TYPE)
        }.asLiveData
    }
}