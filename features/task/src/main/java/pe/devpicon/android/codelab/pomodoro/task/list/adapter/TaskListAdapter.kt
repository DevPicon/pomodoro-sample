package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.devpicon.android.codelab.pomodoro.task.databinding.RowTaskItemBinding

class TaskListAdapter : ListAdapter<TaskListItem, TaskListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding = RowTaskItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
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
