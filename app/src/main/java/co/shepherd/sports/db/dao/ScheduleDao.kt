package co.shepherd.sports.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.db.entities.ScheduleEntity

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM Schedule")
    fun getSchedule(): LiveData<ScheduleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: ScheduleEntity)

    @Transaction
    fun deleteAndInsert(schedule: ScheduleEntity) {
        deleteAll()
        insertSchedule(schedule)
    }

    @Update
    fun updateSchedule(schedule: ScheduleEntity)

    @Delete
    fun deleteSchedule(schedule: ScheduleEntity)

    @Query("DELETE FROM Schedule")
    fun deleteAll()

    @Query("Select count(*) from Events")
    fun getCount(): Int
}