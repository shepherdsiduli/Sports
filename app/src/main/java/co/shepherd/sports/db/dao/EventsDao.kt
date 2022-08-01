package co.shepherd.sports.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import co.shepherd.sports.db.entities.EventsEntity

@Dao
interface EventsDao {
    @Query("SELECT * FROM Events")
    fun getEvents(): LiveData<EventsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: EventsEntity)

    @Transaction
    fun deleteAndInsert(events: EventsEntity) {
        deleteAll()
        insertEvents(events)
    }

    @Update
    fun updateEvents(events: EventsEntity)

    @Delete
    fun deleteEvents(events: EventsEntity)

    @Query("DELETE FROM Events")
    fun deleteAll()

    @Query("Select count(*) from Events")
    fun getCount(): Int
}