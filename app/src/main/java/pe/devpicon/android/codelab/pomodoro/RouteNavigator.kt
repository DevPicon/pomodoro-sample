package pe.devpicon.android.codelab.pomodoro

import androidx.navigation.NavController
import pe.devpicon.android.codelab.pomodoro.login.LoginFragmentDirections
import pe.devpicon.android.codelab.pomodoro.login.LoginNavigator
import java.lang.ref.WeakReference

object RouteNavigator : LoginNavigator {
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
}
