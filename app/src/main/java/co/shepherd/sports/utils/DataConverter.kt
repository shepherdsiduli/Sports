package co.shepherd.sports.utils

import androidx.room.TypeConverter
import co.shepherd.sports.domain.model.EventsResponse
import co.shepherd.sports.domain.model.ScheduleResponse
import com.google.gson.Gson

object DataConverter {

    @TypeConverter
    fun fromEventsResponse(sh: EventsResponse): String {
        return Gson().toJson(sh)
    }
    @TypeConverter
    fun toEventsResponse(sh: String): EventsResponse {
        return Gson().fromJson(sh,EventsResponse::class.java)
    }

    @TypeConverter
    fun fromScheduleResponse(sh: ScheduleResponse): String {
        return Gson().toJson(sh)
    }
    @TypeConverter
    fun toScheduleResponse(sh: String): ScheduleResponse {
        return Gson().fromJson(sh,ScheduleResponse::class.java)
    }
}