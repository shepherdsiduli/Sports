package co.shepherd.sports.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.shepherd.sports.domain.model.ScheduleResponse

@Entity(tableName = "Schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val schedule: ScheduleResponse
)