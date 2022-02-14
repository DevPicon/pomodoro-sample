package pe.devpicon.android.codelab.pomodoro.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PomodoroCodelabApp : Application() {

    lateinit var applicationModule:AppModule

    override fun onCreate() {
        super.onCreate()

        applicationModule = AppModule
    }
}
