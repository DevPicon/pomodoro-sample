package pe.devpicon.android.codelab.pomodoro.app

import android.app.Application

class PomodoroCodelabApp : Application() {

    lateinit var applicationModule:AppModule

    override fun onCreate() {
        super.onCreate()

        applicationModule = AppModule
    }
}
