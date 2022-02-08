package pe.devpicon.android.codelab.pomodoro.domain.common

class FakeErrorHandler : ErrorHandler {
    override fun getError(throwable: Throwable): Error {
        return Error.GenericError(message = throwable.message ?: "Unexpected error")
    }

}
