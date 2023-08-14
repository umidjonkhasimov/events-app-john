package uz.gita.eventsapp_john.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.eventsapp_john.data.model.EventsData

interface EventsUseCase {
    fun getAllDisableEvents(): Flow<List<EventsData>>

    fun getAllEnableEvents(): Flow<List<EventsData>>

    fun updateEventStateToDisable(eventId: Int): Flow<Unit>

    fun updateEventStateToEnable(eventId: Int): Flow<Unit>
}