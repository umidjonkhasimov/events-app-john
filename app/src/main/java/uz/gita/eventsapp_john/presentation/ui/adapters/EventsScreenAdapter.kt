package uz.gita.eventsapp_john.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.eventsapp_john.R
import uz.gita.eventsapp_john.data.model.EventsData
import uz.gita.eventsapp_john.databinding.ItemOfScreenBinding

class EventsScreenAdapter :
    ListAdapter<EventsData, EventsScreenAdapter.EventViewHolder>(EventsDiffUtil) {
    private var onLongClickItemListener: ((id: Int) -> Unit)? = null

    private object EventsDiffUtil : DiffUtil.ItemCallback<EventsData>() {
        override fun areItemsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
            oldItem == newItem

    }

    inner class EventViewHolder(private val binding: ItemOfScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.container.setOnClickListener {
                val data = getItem(adapterPosition)
                onLongClickItemListener?.invoke(data.id)
            }
        }

        fun bind() {
            binding.eventText.text =
                itemView.resources.getString(getItem(adapterPosition).eventName)
            binding.eventImage.setImageResource(getItem(adapterPosition).eventIcon)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemOfScreenBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_of_screen, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnLongClickItemListener(block: (id: Int) -> Unit) {
        onLongClickItemListener = block
    }
}