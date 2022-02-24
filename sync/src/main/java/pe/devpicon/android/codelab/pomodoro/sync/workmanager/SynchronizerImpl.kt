package pe.devpicon.android.codelab.pomodoro.sync.workmanager

import android.content.Context
import android.os.Build
import androidx.work.*
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncType
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.workers.InsertTaskWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class SynchronizerImpl(
    context: Context
) : Synchronizer {

    private var workManager: WorkManager = WorkManager.getInstance(context)

    override fun performSync(task: TaskApi, type: SyncType) {
        val requestBuilder = OneTimeWorkRequest.Builder(getWorkByType(type))
        requestBuilder.setInputData(buildData(task))
        requestBuilder.setConstraints(getGeneralConstraints())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestBuilder.setBackoffCriteria(
                BackoffPolicy.LINEAR,
                Duration.ofMillis(OneTimeWorkRequest.MIN_BACKOFF_MILLIS)
            )
        } else {
            requestBuilder.setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
        }

        workManager.enqueue(requestBuilder.build())
    }

    private fun getGeneralConstraints(): Constraints {
        val constraints = Constraints.Builder()
        constraints.setRequiredNetworkType(NetworkType.CONNECTED)
        return constraints.build()
    }

    private fun buildData(task: TaskApi): Data {
        val data = Data.Builder()

        data.putLong(InsertTaskWorker.TASK_ID_ARGS, task.id)
        data.putString(InsertTaskWorker.TASK_NAME_ARGS, task.name)
        data.putLong(InsertTaskWorker.TASK_CREATION_DATE_ARGS, task.creationDate.time)
        data.putInt(InsertTaskWorker.TASK_DONE_POMODOROS_ARGS, task.donePomodoros)
        data.putInt(InsertTaskWorker.TASK_ESTIMATED_POMODOROS_ARGS, task.estimatedPomodoros)
        data.putInt(InsertTaskWorker.TASK_SHORT_BREAKS_ARGS, task.shortBreaks)
        data.putInt(InsertTaskWorker.TASK_LONG_BREAKS_ARGS, task.longBreaks)
        data.putBoolean(InsertTaskWorker.TASK_COMPLETED_ARGS, task.completed)

        return data.build()

    }

    private fun getWorkByType(type: SyncType): Class<out ListenableWorker> {
        return when (type) {
            SyncType.INSERT -> InsertTaskWorker::class.java
            SyncType.DELETE -> TODO()
            SyncType.UPDATE -> TODO()
        }
    }
}
