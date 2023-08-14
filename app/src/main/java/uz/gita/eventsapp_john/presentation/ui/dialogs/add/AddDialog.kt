package uz.gita.eventsapp_john.presentation.ui.dialogs.add

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.data.model.EventsData
import uz.gita.eventsapp_john.databinding.DialogAddBinding
import uz.gita.eventsapp_john.presentation.ui.adapters.AddDialogAdapter
import uz.gita.eventsapp_john.presentation.ui.dialogs.add.viewmodel.AddDialogViewModel
import uz.gita.eventsapp_john.presentation.ui.dialogs.add.viewmodel.AddDialogViewModelImpl

@AndroidEntryPoint
class AddDialog : DialogFragment(R.layout.dialog_add) {
    private val binding by viewBinding(DialogAddBinding::bind)
    private val viewModel: AddDialogViewModel by viewModels<AddDialogViewModelImpl>()
    private val adapter = AddDialogAdapter()
    private var pos = -1

    private var onCLickOkListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllDisableEventsLiveData.observe(viewLifecycleOwner, getAllDisableEventsObserver)
        viewModel.closeDialogLiveData.observe(viewLifecycleOwner, closeDialogObserver)

        adapter.setOnClickItemListener {
            pos = it
            if (pos != -1) {
                binding.okBtn.isEnabled = true
            }
        }

        binding.okBtn.setOnClickListener { viewModel.updateEventStateToEnable(pos) }
        binding.cancelBtn.setOnClickListener { dismiss() }
    }

    private val getAllDisableEventsObserver = Observer<List<EventsData>> {
        adapter.submitList(it)
        binding.recyclerDialog.adapter = adapter
        binding.recyclerDialog.layoutManager = LinearLayoutManager(requireContext())
    }

    private val closeDialogObserver = Observer<Unit> {
        onCLickOkListener?.invoke()
        dismiss()
    }

    fun setOnClickOkClickListener(block: () -> Unit) {
        onCLickOkListener = block
    }
}