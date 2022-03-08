package pe.devpicon.android.codelab.pomodoro.task.list

import pe.devpicon.android.codelab.pomodoro.task.list.adapter.TaskListItem

sealed class TaskListScreenEvent {
    object Load : TaskListScreenEvent()
    object AddTaskPressed : TaskListScreenEvent()
    data class OnDeleteActionItemClicked (val selectedItemList:List<TaskListItem>) : TaskListScreenEvent()
    object OnStartActionMode : TaskListScreenEvent()
    object OnFinishActionMode : TaskListScreenEvent()
}
