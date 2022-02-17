package pe.devpicon.android.codelab.pomodoro.task.list

sealed class TaskListScreenEvent {
    object Load : TaskListScreenEvent()
    object AddTaskPressed : TaskListScreenEvent()
}
