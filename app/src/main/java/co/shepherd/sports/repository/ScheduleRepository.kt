package co.shepherd.sports.repository

import androidx.lifecycle.LiveData
import co.shepherd.sports.core.Constants
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.domain.datasources.schedule.ScheduleLocalDataSource
import co.shepherd.sports.domain.datasources.schedule.ScheduleRemoteDataSource
import co.shepherd.sports.domain.model.ScheduleResponse
import co.shepherd.sports.utils.domain.FrequencyLimiter
import co.shepherd.sports.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleRemoteDataSource: ScheduleRemoteDataSource,
    private val scheduleLocalDataSource: ScheduleLocalDataSource
) {
    private val scheduleRateLimit = FrequencyLimiter<String>(30, TimeUnit.SECONDS)

    fun loadSchedule(
        fetchRequired: Boolean
    ): LiveData<Resource<ScheduleEntity>> {
        return object : NetworkBoundResource<ScheduleEntity, ScheduleResponse>() {
            override fun saveCallResponse(item: ScheduleResponse) =
                scheduleLocalDataSource.insertSchedule(
                    item
                )

            override fun shouldFetch(data: ScheduleEntity?): Boolean = fetchRequired

            override fun loadFromDatabase(): LiveData<ScheduleEntity> =
                scheduleLocalDataSource.getSchedule()

            override fun createCall(): Single<ScheduleResponse> =
                scheduleRemoteDataSource.getSchedule()

            override fun onFetchFailed() = scheduleRateLimit.reset(Constants.SportsNetworkService.RATE_LIMITER_TYPE)
        }.asLiveData
    }
}