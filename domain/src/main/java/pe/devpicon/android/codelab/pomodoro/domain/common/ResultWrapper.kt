package pe.devpicon.android.codelab.pomodoro.domain.common

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val error: Error) : ResultWrapper<Nothing>()
}
