package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import android.text.format.DateFormat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pe.devpicon.android.codelab.pomodoro.task.databinding.RowTaskItemBinding

class TaskListViewHolder(
    private val binding: RowTaskItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TaskListItem) {
        with(binding) {
            tvTaskName.text = data.name
            tvTaskDate.text = DateFormat.getDateFormat(itemView.context).format(data.creationDate)
            tvEstPommodoros.text = data.estimatedPomodoros
            ivTaskCheck.isVisible = data.completed
        }
    }
}
