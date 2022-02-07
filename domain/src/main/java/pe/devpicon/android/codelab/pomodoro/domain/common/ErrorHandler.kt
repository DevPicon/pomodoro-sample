package pe.devpicon.android.codelab.pomodoro.domain.common

interface ErrorHandler {
    fun getError(throwable: Throwable): Error
}
