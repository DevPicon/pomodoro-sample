package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.isEmpty
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.devpicon.android.codelab.pomodoro.task.databinding.RowTaskItemBinding

class TaskListAdapter(
    private val onItemSelector: OnItemClick<TaskListItem>
) : ListAdapter<TaskListItem, TaskListViewHolder>(DIFF_CALLBACK) {

    private val selectedItems = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding = RowTaskItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                toggleItemSelection(holder.adapterPosition)
                onItemSelector.onItemSelectedClick(selectedItems.size())
            } else {
                onItemSelector.onItemClick(getItem(holder.adapterPosition))
            }
        }

        holder.itemView.setOnLongClickListener {
            if (selectedItems.isEmpty()) {
                toggleItemSelection(holder.adapterPosition)
                onItemSelector.onLongPress(selectedItems.size())
                true
            } else {
                false
            }
        }

        holder.bind(getItem(holder.adapterPosition))
    }

    private fun toggleItemSelection(adapterPosition: Int) {
        if (selectedItems[adapterPosition, false]) {
            selectedItems.delete(adapterPosition)
        } else {
            selectedItems.put(adapterPosition, true)
        }
    }

    fun getSelectedItems(): List<TaskListItem> {
        val items = ArrayList<TaskListItem>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            items.add(currentList[selectedItems.keyAt(i)])
        }
        return items
    }

    override fun submitList(list: MutableList<TaskListItem>?) {
        clearSelection()
        super.submitList(list)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelection() {
        if (selectedItems.isNotEmpty()) {
            selectedItems.clear()
            notifyDataSetChanged()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskListItem>() {
            override fun areItemsTheSame(oldItem: TaskListItem, newItem: TaskListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TaskListItem, newItem: TaskListItem): Boolean =
                oldItem == newItem

        }
    }
}
