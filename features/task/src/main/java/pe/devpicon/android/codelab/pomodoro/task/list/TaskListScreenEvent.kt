package pe.devpicon.android.codelab.pomodoro.task.list

sealed class TaskListScreenEvent {
    object Load : TaskListScreenEvent()
    object AddTaskPressed : TaskListScreenEvent()
    object OnDeleteActionItemClicked : TaskListScreenEvent()
    object OnStartActionMode : TaskListScreenEvent()
    object OnFinishActionMode : TaskListScreenEvent()
}
