package pe.devpicon.android.codelab.pomodoro.sync.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.workers.DeleteTaskWorker
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.workers.InsertTaskWorker

class TaskWorkerFactory(
    private val userLocalDataSource: UserLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val errorHandler: SyncErrorHandler
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            InsertTaskWorker::class.java.name -> InsertTaskWorker(
                appContext,
                workerParameters, userLocalDataSource, taskRemoteDataSource, errorHandler
            )
            DeleteTaskWorker::class.java.name -> DeleteTaskWorker(
                appContext, workerParameters, userLocalDataSource, taskRemoteDataSource, errorHandler
            )
            else -> null
        }
    }
}
