package co.shepherd.sports.domain.datasources.events

import co.shepherd.sports.db.dao.EventsDao
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.domain.model.EventsResponse
import javax.inject.Inject

class EventsLocalDataSource @Inject constructor(private val eventsDao: EventsDao) {

    fun getEvents() = eventsDao.getEvents()

    fun insertEvents(events: EventsResponse) = eventsDao.deleteAndInsert(
        EventsEntity(1, events))
}