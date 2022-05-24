package pe.devpicon.android.codelab.pomodoro.sync

import android.content.Context
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncManager
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncType
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.Synchronizer
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.SynchronizerImpl

class SyncManagerImpl(
    context: Context
) : SyncManager {

    private var synchronizer: Synchronizer = SynchronizerImpl(
        context
    )

    override fun performSyncInsertion(task: TaskApi) {
        synchronizer.performSync(task, SyncType.INSERT)
    }

    override fun performSyncDeletion(taskId: Long) {
        synchronizer.performSync(taskId, SyncType.DELETE)
    }
}
