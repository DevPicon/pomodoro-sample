package pe.devpicon.android.codelab.pomodoro.data.sync

sealed class SyncError {
    data class GenericError(val code: Int? = null, val message: String) : SyncError()
    object NetworkError : SyncError()
}
