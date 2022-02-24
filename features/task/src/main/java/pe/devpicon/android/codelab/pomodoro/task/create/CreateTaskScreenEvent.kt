package pe.devpicon.android.codelab.pomodoro.task.create

sealed class CreateTaskScreenEvent {
    data class EditingTask(val name: String, val estimatedPomodoros: Int) : CreateTaskScreenEvent()
    object SaveTask : CreateTaskScreenEvent()
    object OnPressRandomButton : CreateTaskScreenEvent()
}
