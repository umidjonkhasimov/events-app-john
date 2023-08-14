package uz.gita.eventsapp_john.data.model

import uz.gita.eventsapp_john.data.local.entity.EventsEntity

data class EventsData(
    val id: Int,
    val eventIcon: Int,
    val events: String,
    val eventName: Int,
    var eventState: Int
) {
    fun toEventsEntity(): EventsEntity = EventsEntity(
        id = id,
        eventIcon = eventIcon,
        events = events,
        eventName = eventName,
        eventState = eventState
    )
}
