package co.shepherd.sports.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.shepherd.sports.domain.model.EventsResponse

@Entity(tableName = "Events")
data class EventsEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val events: EventsResponse
)