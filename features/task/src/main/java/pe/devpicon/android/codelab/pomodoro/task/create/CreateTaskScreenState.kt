package pe.devpicon.android.codelab.pomodoro.task.create

sealed class CreateTaskScreenState {
    data class Start(
        val name: String = "",
        val estimated: Int = 0,
        val showInlineLoader: Boolean = false
    ) : CreateTaskScreenState()

    object InvalidName : CreateTaskScreenState()
    object Loading : CreateTaskScreenState()
    object Success : CreateTaskScreenState()
}
