package pe.devpicon.android.codelab.pomodoro.sync.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.retry
import androidx.work.WorkerParameters
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler

class DeleteTaskWorker(
    context: Context,
    params: WorkerParameters,
    private val userLocalDataSource: UserLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val errorHandler: SyncErrorHandler
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val taskId = inputData.getLong(InsertTaskWorker.TASK_ID_ARGS, -1L)

        return try {
            taskRemoteDataSource.deleteTask(userLocalDataSource.getUserId(), taskId)
            Result.success()
        } catch (e:Exception){
            errorHandler.getSyncError(e)
            retry()
        }
    }
}
