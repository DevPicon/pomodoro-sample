package pe.devpicon.android.codelab.pomodoro.task.list

import pe.devpicon.android.codelab.pomodoro.task.list.adapter.TaskListItem

sealed class TaskListScreenState {
    object Initial : TaskListScreenState()
    object Loading : TaskListScreenState()
    data class DataLoaded(
        val taskList: List<TaskListItem>
    ) : TaskListScreenState()
    object EmptyState : TaskListScreenState()
    object ShowActionMode : TaskListScreenState()
    object HideActionMode : TaskListScreenState()
}
