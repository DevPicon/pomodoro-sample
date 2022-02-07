package pe.devpicon.android.codelab.pomodoro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    private val navigator by lazy { RouteNavigator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onStop() {
        super.onStop()
        navigator.unbind()
    }
}
