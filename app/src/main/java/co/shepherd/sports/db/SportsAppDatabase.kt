package co.shepherd.sports.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.shepherd.sports.db.dao.EventsDao
import co.shepherd.sports.db.dao.ScheduleDao
import co.shepherd.sports.db.entities.EventEntity
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.utils.DataConverter
@Database(
    entities = [
        EventsEntity::class,
        ScheduleEntity::class,
        EventEntity::class
    ],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class SportsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao
    abstract fun scheduleDao(): ScheduleDao
}