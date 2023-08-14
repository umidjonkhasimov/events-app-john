package uz.gita.eventsapp_john.presentation.ui.screens.events

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.data.model.EventsData
import uz.gita.eventsapp_john.databinding.ScreenEventsBinding
import uz.gita.eventsapp_john.presentation.ui.adapters.EventsScreenAdapter
import uz.gita.eventsapp_john.presentation.ui.dialogs.add.AddDialog
import uz.gita.eventsapp_john.presentation.ui.dialogs.bottom.BottomSheetDialog
import uz.gita.eventsapp_john.presentation.ui.dialogs.delete.DeleteDialog
import uz.gita.eventsapp_john.presentation.ui.screens.events.viewmodel.EventsViewModel
import uz.gita.eventsapp_john.presentation.ui.screens.events.viewmodel.impl.EventsViewModelImpl
import uz.gita.eventsapp_john.utils.ENABLED_ACTIONS
import uz.gita.eventsapp_john.utils.notification.createChannel
import uz.gita.eventsapp_john.utils.service.EventsService

@AndroidEntryPoint
class EventsScreen : Fragment(R.layout.screen_events) {
    private val binding by viewBinding(ScreenEventsBinding::bind)
    private val viewModel: EventsViewModel by viewModels<EventsViewModelImpl>()
    private val adapter = EventsScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel.getAllEnableEventsLiveData.observe(viewLifecycleOwner, getAllEnableEventsObserver)
        viewModel.onClickMoreLiveData.observe(viewLifecycleOwner, onClickMoreObserver)
        viewModel.onClickShareLiveData.observe(viewLifecycleOwner, onClickShareObserver)
        viewModel.onClickRateLiveData.observe(viewLifecycleOwner, onClickRateObserver)
        viewModel.onClickFeedbackLiveData.observe(viewLifecycleOwner, onCLickFeedbackObserver)

        viewModel.setOnClickAddDialogLiveDataListener {
            val addDialog = AddDialog()

            addDialog.setOnClickOkClickListener {
                viewModel.onCLickOkBtnOfAddDialog()
            }

            addDialog.show(childFragmentManager, "AddDialog")
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.onClickAddDialogButton()
        }

        adapter.setOnLongClickItemListener { eventId ->
            val deleteDialog = DeleteDialog()

            deleteDialog.setOnClickYesListener {
                viewModel.updateEventStateToDisable(eventId)
            }

            deleteDialog.show(childFragmentManager, "deleteDialog")
        }
    }

    private val getAllEnableEventsObserver = Observer<List<EventsData>> { list ->
        val arrayList = ArrayList<String>()

        for (i in list.indices) {
            arrayList.add(list[i].events)
        }

        val intent = Intent(requireContext(), EventsService::class.java)
        intent.putStringArrayListExtra(ENABLED_ACTIONS, arrayList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (requireActivity().checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                requireActivity().startForegroundService(intent)
            } else {
                requestPermission(requireActivity(), intent)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requireActivity().startForegroundService(intent)
            } else {
                requireActivity().startService(intent)
            }
        }
        adapter.submitList(list)
        binding.recyclerScreen.adapter = adapter
        binding.recyclerScreen.layoutManager = LinearLayoutManager(requireContext())
    }

    private val onClickMoreObserver = Observer<Unit> {
        val bottomSheetDialog = BottomSheetDialog()

        bottomSheetDialog.setOnClickShareListener {
            viewModel.onClickShare()
        }
        bottomSheetDialog.setOnClickRateListener {
            viewModel.onClickRate()
        }
        bottomSheetDialog.setOnClickFeedbackListener {
            viewModel.onCLickFeedback()
        }

        bottomSheetDialog.show(childFragmentManager, "bottomSheetDialog")

    }
    private val onClickShareObserver = Observer<Unit> {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val body = "https://play.google.com/store/apps/details?id=" + requireActivity().packageName + "\n\n"
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, "Share using:"))
    }
    private val onClickRateObserver = Observer<Unit> {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${requireActivity().packageName}")
            )
        )
    }
    private val onCLickFeedbackObserver = Observer<Unit> {
        val emailIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "umidjon.khasimov@gmail.com"))
        startActivity(emailIntent)
    }

    private fun requestPermission(activity: FragmentActivity, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionX.init(activity)
                .permissions(Manifest.permission.POST_NOTIFICATIONS)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        createChannel(activity)
                        requireActivity().startForegroundService(intent)
                    }
                }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(activity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.moreMenu -> {
                viewModel.onClickMore()
                true
            }

            else -> false
        }
    }
}