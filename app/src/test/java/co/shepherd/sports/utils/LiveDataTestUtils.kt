package co.shepherd.sports.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import co.shepherd.sports.db.entities.EventsEntity
import co.shepherd.sports.db.entities.ScheduleEntity
import co.shepherd.sports.domain.model.Event
import co.shepherd.sports.domain.model.EventsResponse
import co.shepherd.sports.domain.model.ScheduleResponse
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

// Data Generators
fun generateScheduleEntity(): ScheduleEntity {
    val event = Event("1","Liverpool v Porto","UEFA Champions League", "2022-08-01T01:38:40.472Z", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20", null)
    val schedule = listOf(event)
    var response = ScheduleResponse(schedule)
    return ScheduleEntity(1, response)
}

fun generateEventsEntity(): EventsEntity {
    val event = Event("1","Liverpool v Porto","UEFA Champions League", "2022-08-01T01:38:40.472Z", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/promo.mp4?alt=media")
    val events = listOf(event)
    var response = EventsResponse(events)
    return EventsEntity(1, response)
}

fun createSampleScheduleResponse(): List<Event>{
    val event = Event("1","Liverpool v Porto","UEFA Champions League", "2022-08-01T01:38:40.472Z", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20", null)
    return listOf(event)
}

fun createSampleEventsResponse(): List<Event>{
    val event = Event("1","Liverpool v Porto","UEFA Champions League", "2022-08-01T01:38:40.472Z", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20", "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/promo.mp4?alt=media")
    return listOf(event)
}