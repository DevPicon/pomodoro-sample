package pe.devpicon.android.codelab.pomodoro.task

import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.task.list.adapter.TaskListItem
import javax.inject.Inject

class ViewMapper
@Inject constructor() {
    fun fromModelToView(tasks: List<Task>): List<TaskListItem> {
        return tasks.map {
            TaskListItem(
                it.id ?: -1, it.name, it.creationDate,
                it.estimatedPomodoros.toString(), it.completed
            )
        }
    }
}
