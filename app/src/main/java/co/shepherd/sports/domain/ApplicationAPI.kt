package co.shepherd.sports.domain

import co.shepherd.sports.core.Constants
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.model.ScheduleResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApplicationAPI {
    @GET(Constants.SportsNetworkService.EVENTS)
    fun getEvents(): Single<List<Event>> //Single<EventsResponse>

    @GET(Constants.SportsNetworkService.SCHEDULE)
    fun getSchedule(): Single<ScheduleResponse>
}