package pe.devpicon.android.codelab.pomodoro.sync.workmanager

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncType

interface Synchronizer {
    fun performSync(task: TaskApi, type: SyncType)
    fun performSync(taskId: Long, type: SyncType)
}
