package pe.devpicon.android.codelab.pomodoro.data.common

import com.google.firebase.auth.FirebaseAuthException
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): Error {
        return when (throwable) {
            is FirebaseAuthException -> {
                loginErrorCodeMapper(throwable)
            }
            else -> {
                Error.GenericError(
                    null,
                    throwable.message ?: ""
                )
            }
        }
    }

    private fun loginErrorCodeMapper(throwable: FirebaseAuthException): Error {
        return when (throwable.errorCode) {
            "ERROR_OPERATION_NOT_ALLOWED" -> Error.GenericError(
                message = throwable.message ?: ""
            )
            "ERROR_WEAK_PASSWORD" -> Error.UserPasswordError(throwable.message ?: "")
            "ERROR_INVALID_EMAIL" -> Error.UserEmailError(throwable.message ?: "")
            "ERROR_WRONG_PASSWORD" -> Error.UserPasswordError(throwable.message ?: "")
            "ERROR_EMAIL_ALREADY_IN_USE" -> Error.UserEmailError(throwable.message ?: "")
            "ERROR_INVALID_CREDENTIAL" -> Error.UserEmailError(throwable.message ?: "")
            "ERROR_USER_NOT_FOUND" -> Error.UserEmailError(throwable.message ?: "")
            "ERROR_USER_DISABLED" -> Error.UserEmailError(throwable.message ?: "")
            "ERROR_TOO_MANY_REQUESTS" -> Error.GenericError(message = throwable.message ?: "")
            else -> Error.GenericError(message = throwable.message ?: "")
        }
    }
}
