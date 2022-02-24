package pe.devpicon.android.codelab.pomodoro.sync.workmanager

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncType

/**
 * Here we declare the required functions to perform the Synchronization
 */
interface Synchronizer {
    fun performSync(task: TaskApi, type: SyncType)
}
