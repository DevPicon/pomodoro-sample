package pe.devpicon.android.codelab.pomodoro.data.sync

interface SyncErrorHandler {
    fun getSyncError(throwable: Throwable): SyncError
}
