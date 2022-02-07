package pe.devpicon.android.codelab.pomodoro

import androidx.navigation.NavController

class RouteNavigator {
    private var navController:NavController? = null

    fun bind(navController: NavController){
        this.navController = navController
    }

    fun unbind(){
        this.navController = null
    }
}
