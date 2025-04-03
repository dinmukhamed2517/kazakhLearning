package kz.digis.kazakhlearning.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kz.digis.kazakhlearning.data.models.Choice
import kz.digis.kazakhlearning.databinding.ItemMessageTrainerBinding
import kz.digis.kazakhlearning.databinding.ItemMessageUserBinding
import kz.digis.kazakhlearning.presentation.base.BaseMessageViewHolder
import java.text.SimpleDateFormat
import java.util.Date

class ItemMessageAdapter : ListAdapter<Choice, BaseMessageViewHolder<*>>(MessageDiffUtils()) {

    class MessageDiffUtils : DiffUtil.ItemCallback<Choice>() {
        override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem.index == newItem.index
        }

        override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<*> {
        return when (viewType) {
            VIEW_TYPE_TRAINER -> MessageViewHolder(
                ItemMessageTrainerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_TYPE_USER -> MessageUserViewHolder(
                ItemMessageUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun submitList(list: MutableList<Choice>?) {
        val newList: MutableList<Choice> = mutableListOf()
        list?.forEach {
            newList.add(it)
        }

        super.submitList(newList)
    }

    override fun onBindViewHolder(holder: BaseMessageViewHolder<*>, position: Int) {
        holder.bindView(getItem(position))

    }


    inner class MessageViewHolder(binding: ItemMessageTrainerBinding) :
        BaseMessageViewHolder<ItemMessageTrainerBinding>(binding) {
        override fun bindView(item: Choice) {
            with(binding) {
                content.text = item.message.content
                time.text = getCurrentHourAndMinute()
            }
        }
    }

    inner class MessageUserViewHolder(binding: ItemMessageUserBinding) :
        BaseMessageViewHolder<ItemMessageUserBinding>(binding) {
        override fun bindView(item: Choice) {
            with(binding) {
                content.text = item.message.content
                time.text = getCurrentHourAndMinute()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).message.role) {
            "assistant" -> VIEW_TYPE_TRAINER
            "user" -> VIEW_TYPE_USER
            else -> throw IllegalArgumentException("Invalid message role")
        }
    }

    companion object {
        private const val VIEW_TYPE_TRAINER = 1
        private const val VIEW_TYPE_USER = 2
    }
}


fun getCurrentHourAndMinute(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val date = Date(currentTimeMillis)

    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(date)
}