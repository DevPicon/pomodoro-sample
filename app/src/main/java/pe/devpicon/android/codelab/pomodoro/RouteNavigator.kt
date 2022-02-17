package pe.devpicon.android.codelab.pomodoro

import androidx.navigation.NavController
import pe.devpicon.android.codelab.pomodoro.login.LoginFragmentDirections
import pe.devpicon.android.codelab.pomodoro.login.LoginNavigator
import javax.inject.Inject

class RouteNavigator
@Inject constructor() : LoginNavigator {
    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    override fun navigateOnLoginSuccess() {
        navController?.navigate(LoginFragmentDirections.actionLoginFragmentToTaskListFragment())
    }
}
