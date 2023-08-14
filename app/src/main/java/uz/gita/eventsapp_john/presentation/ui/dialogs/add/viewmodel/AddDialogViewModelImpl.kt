package uz.gita.eventsapp_john.presentation.ui.dialogs.add.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.eventsapp_john.data.model.EventsData
import uz.gita.eventsapp_john.domain.usecase.EventsUseCase
import javax.inject.Inject

@HiltViewModel
class AddDialogViewModelImpl
@Inject constructor(
    private val useCase: EventsUseCase
) : ViewModel(),
    AddDialogViewModel {

    init {
        loadData()
    }

    override val getAllDisableEventsLiveData = MutableLiveData<List<EventsData>>()
    override val closeDialogLiveData = MutableLiveData<Unit>()

    override fun updateEventStateToEnable(eventId: Int) {
        useCase.updateEventStateToEnable(eventId).onEach {
            closeDialogLiveData.value = Unit
        }.launchIn(viewModelScope)
    }

    private fun loadData() {
        useCase.getAllDisableEvents().onEach {
            getAllDisableEventsLiveData.value = it
        }.launchIn(viewModelScope)
    }
}