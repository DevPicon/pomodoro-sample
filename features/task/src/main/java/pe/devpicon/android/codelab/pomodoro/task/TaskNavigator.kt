package pe.devpicon.android.codelab.pomodoro.task

interface TaskNavigator {
    fun navigateOnToCreateTask()
    fun onBack()
    fun navigateOnToTimer(id: Long, name: String?)
}
