package uz.gita.eventsapp_john.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.data.model.EventsData
import uz.gita.eventsapp_john.databinding.ItemOfDialogBinding

class AddDialogAdapter :
    ListAdapter<EventsData, AddDialogAdapter.AddDialogViewHolder>(AddDialogDiffUtil) {
    private var onClickItemListener: ((Int) -> Unit)? = null
    private var selected = -1

    private object AddDialogDiffUtil : DiffUtil.ItemCallback<EventsData>() {
        override fun areItemsTheSame(oldItem: EventsData, newItem: EventsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventsData, newItem: EventsData): Boolean {
            return oldItem == newItem
        }
    }

    inner class AddDialogViewHolder(private val binding: ItemOfDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.container.setOnClickListener {
                if (selected > -1 && selected != adapterPosition) {
                    getItem(selected).eventState = 0
                    notifyItemChanged(selected)
                }

                selected = adapterPosition
                binding.eventDialogRadioButton.isChecked = true
                onClickItemListener?.invoke(getItem(adapterPosition).id)
            }
            binding.eventDialogRadioButton.setOnClickListener {
                if (selected > -1 && selected != adapterPosition) {
                    getItem(selected).eventState = 0
                    notifyItemChanged(selected)
                }

                selected = adapterPosition
                binding.eventDialogRadioButton.isChecked = true
                onClickItemListener?.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind() {
            binding.eventDialogText.text =
                itemView.resources.getString(getItem(adapterPosition).eventName)
            binding.eventDialogRadioButton.isChecked =
                getItem(adapterPosition).eventState == 1

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddDialogViewHolder {
        return AddDialogViewHolder(
            ItemOfDialogBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddDialogViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickItemListener(block: (Int) -> Unit) {
        onClickItemListener = block
    }
}