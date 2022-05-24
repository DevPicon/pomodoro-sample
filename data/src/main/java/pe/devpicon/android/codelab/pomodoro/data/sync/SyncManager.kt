package pe.devpicon.android.codelab.pomodoro.data.sync

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi

interface SyncManager {
    fun performSyncInsertion(task: TaskApi)
    fun performSyncDeletion(taskId: Long)
}
