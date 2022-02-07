package pe.devpicon.android.codelab.pomodoro.domain.common

sealed class ResultWrapper {
    data class Success<out T>(val value: T) : ResultWrapper()
    data class Failure(val error: Error) : ResultWrapper()
}
