package co.shepherd.sports.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.repository.ScheduleRepository
import co.shepherd.sports.ui.schedule.ScheduleViewState
import co.shepherd.sports.utils.UseCaseLiveData
import co.shepherd.sports.utils.domain.Resource
import javax.inject.Inject

class ScheduleUseCase @Inject
internal constructor(
    private val repository: ScheduleRepository
) : UseCaseLiveData<ScheduleViewState, ScheduleUseCase.ScheduleParams, ScheduleRepository>() {
    override fun getRepository(): ScheduleRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: ScheduleParams?): LiveData<ScheduleViewState> {
        return repository.loadSchedule(
            params?.fetchRequired
                ?: false
        ).map {
            onEventsResultReady(it)
        }
    }

    private fun onEventsResultReady(resource: Resource<ScheduleEntity>): ScheduleViewState {
        return ScheduleViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class ScheduleParams(
        val fetchRequired: Boolean
    ) : Params()
}