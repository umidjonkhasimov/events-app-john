package uz.gita.eventsapp_john.presentation.ui.dialogs.add.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsapp_john.data.model.EventsData

interface AddDialogViewModel {
    val getAllDisableEventsLiveData: LiveData<List<EventsData>>
    val closeDialogLiveData : LiveData<Unit>

    fun updateEventStateToEnable(eventId: Int)
}