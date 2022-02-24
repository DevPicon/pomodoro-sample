package pe.devpicon.android.codelab.pomodoro.sync

import android.content.Context
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncManager
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncType
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.Synchronizer
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.SynchronizerImpl

class SyncManagerImpl(
    context: Context,
    userLocalDataSource: UserLocalDataSource,
    taskRemoteDataSource: TaskRemoteDataSource,
    errorHandler: SyncErrorHandler
) : SyncManager {

    private var synchronizer: Synchronizer = SynchronizerImpl(
        context, userLocalDataSource, taskRemoteDataSource, errorHandler
    )

    override fun performSyncInsertion(task: TaskApi) {
        synchronizer.performSync(task, SyncType.INSERT)
    }
}
