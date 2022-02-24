package pe.devpicon.android.codelab.pomodoro.sync.workmanager.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker.Result.retry
import androidx.work.WorkerParameters
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler
import java.util.*

class InsertTaskWorker(
    context: Context,
    params: WorkerParameters,
    private val userLocalDataSource: UserLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val errorHandler: SyncErrorHandler
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val task = dataToTaskModel(inputData)

        return try {
            taskRemoteDataSource.insertTask(userLocalDataSource.getUserId(), task)
            Result.success()
        } catch (e: Exception) {
            errorHandler.getSyncError(e)
            retry()
        }
    }

    private fun dataToTaskModel(inputData: Data): TaskApi {
        return TaskApi(
            inputData.getLong(TASK_ID_ARGS, -1L),
            inputData.getString(TASK_NAME_ARGS) ?: "",
            Date(inputData.getLong(TASK_CREATION_DATE_ARGS, -1L)),
            inputData.getInt(TASK_DONE_POMODOROS_ARGS, -1),
            inputData.getInt(TASK_ESTIMATED_POMODOROS_ARGS, -1),
            inputData.getInt(TASK_SHORT_BREAKS_ARGS, -1),
            inputData.getInt(TASK_LONG_BREAKS_ARGS, -1),
            inputData.getBoolean(TASK_COMPLETED_ARGS, false)
        )
    }

    companion object {
        const val TASK_ID_ARGS = "task.id.args"
        const val TASK_NAME_ARGS = "task.name.args"
        const val TASK_CREATION_DATE_ARGS = "task.creation.args"
        const val TASK_DONE_POMODOROS_ARGS = "task.done_pomodoros.args"
        const val TASK_ESTIMATED_POMODOROS_ARGS = "task.estimated_pomodoros.args"
        const val TASK_SHORT_BREAKS_ARGS = "task.short_breaks.args"
        const val TASK_LONG_BREAKS_ARGS = "task.long_breaks.args"
        const val TASK_COMPLETED_ARGS = "task.completed.args"
    }
}
