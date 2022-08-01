package co.shepherd.sports.domain

import co.shepherd.sports.core.Constants
import co.shepherd.sports.domain.model.Event
import io.reactivex.Single
import retrofit2.http.GET

interface ApplicationAPI {
    @GET(Constants.SportsNetworkService.EVENTS)
    fun getEvents(): Single<List<Event>>

    @GET(Constants.SportsNetworkService.SCHEDULE)
    fun getSchedule(): Single<List<Event>>
}