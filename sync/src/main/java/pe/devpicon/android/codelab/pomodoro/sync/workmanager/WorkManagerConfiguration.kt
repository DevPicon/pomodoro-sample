package pe.devpicon.android.codelab.pomodoro.sync.workmanager

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler

class WorkManagerConfiguration(
    private val userLocalDataSource: UserLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val errorHandler: SyncErrorHandler
) : Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        val workerFactory = DelegatingWorkerFactory()
        workerFactory.addFactory(
            TaskWorkerFactory(userLocalDataSource, taskRemoteDataSource, errorHandler)
        )
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}
