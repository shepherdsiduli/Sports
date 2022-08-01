package co.shepherd.sports.ui.schedule

import co.shepherd.sports.core.BaseViewState
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.utils.domain.Status

class ScheduleViewState(
val status: Status,
val error: String? = null,
val data: ScheduleEntity? = null
) : BaseViewState(status, error) {
    fun getSchedule() = data
}