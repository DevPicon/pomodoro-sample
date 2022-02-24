package pe.devpicon.android.codelab.pomodoro

import androidx.navigation.NavController
import pe.devpicon.android.codelab.pomodoro.login.LoginFragmentDirections
import pe.devpicon.android.codelab.pomodoro.login.LoginNavigator
import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator
import pe.devpicon.android.codelab.pomodoro.task.list.TaskListFragmentDirections
import pe.devpicon.android.codelab.pomodoro.timer.TimerNavigator
import java.lang.ref.WeakReference

object RouteNavigator : LoginNavigator, TaskNavigator, TimerNavigator {
    private var navController: WeakReference<NavController>? = null

    fun bind(nav: NavController) {
        this.navController = WeakReference<NavController>(nav)
    }

    fun unbind() {
        this.navController = null
    }

    override fun navigateOnLoginSuccess() {
        navController?.get()
            ?.navigate(LoginFragmentDirections.actionLoginFragmentToTaskListFragment())
    }

    override fun navigateOnToCreateTask() {
        navController?.get()
            ?.navigate(TaskListFragmentDirections.actionTaskListFragmentToCreateTask())
    }

    override fun onBack() {
        navController?.get()?.popBackStack()
    }

    override fun navigateOnToTimer(id: Long, name: String?) {
        navController?.get()
            ?.navigate(
                TaskListFragmentDirections.actionTaskListFragmentToTimer(id, name)
            )
    }
}
