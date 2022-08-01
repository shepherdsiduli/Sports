package co.shepherd.sports.domain.datasources.schedule

import co.shepherd.sports.domain.ApplicationAPI
import co.shepherd.sports.domain.model.Event
import io.reactivex.Single
import javax.inject.Inject

class ScheduleRemoteDataSource @Inject constructor(private val api: ApplicationAPI) {

    fun getSchedule(): Single<List<Event>> = api.getSchedule()
}