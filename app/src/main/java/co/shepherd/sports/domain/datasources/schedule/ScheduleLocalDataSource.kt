package co.shepherd.sports.domain.datasources.schedule

import co.shepherd.sports.db.dao.ScheduleDao
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.domain.model.ScheduleResponse
import javax.inject.Inject

class ScheduleLocalDataSource @Inject constructor(private val scheduleDao: ScheduleDao) {

    fun getSchedule() = scheduleDao.getSchedule()

    fun insertSchedule(schedule: ScheduleResponse) = scheduleDao.deleteAndInsert(
        ScheduleEntity(1, schedule)
    )
}