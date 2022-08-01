package co.shepherd.sports.db.entities

import android.os.Parcelable
import androidx.room.*
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.model.EventsResponse
import co.shepherd.sports.domain.model.ScheduleResponse
import co.shepherd.sports.utils.DataConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Schedule")
data class ScheduleEntity(
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    val id: Int,
//
//  //  @ColumnInfo(name = "schedule")
//    @Embedded
//    val schedule: List<Event?>

    @PrimaryKey(autoGenerate = false) val id: Int,
//@ColumnInfo(name = "events")
//    @Embedded
//    val events: List<Event?>? = null

  //  @ColumnInfo(name = "current_state_marker") val currentState: Short,
    val schedule: ScheduleResponse
)