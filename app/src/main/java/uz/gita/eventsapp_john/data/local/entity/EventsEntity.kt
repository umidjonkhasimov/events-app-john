package uz.gita.eventsapp_john.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.eventsapp_john.data.model.EventsData


@Entity(tableName = "events")
data class EventsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventIcon: Int,
    val events: String,
    val eventName: Int,
    val eventState: Int = 0
) {
    fun toEventData(): EventsData = EventsData(
        id = id,
        eventIcon = eventIcon,
        events = events,
        eventName = eventName,
        eventState = eventState
    )
}