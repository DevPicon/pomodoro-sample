package pe.devpicon.android.codelab.pomodoro.app

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PomodoroCodelabApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: DelegatingWorkerFactory
    lateinit var applicationModule: AppModule

    override fun onCreate() {
        super.onCreate()

        applicationModule = AppModule
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}
