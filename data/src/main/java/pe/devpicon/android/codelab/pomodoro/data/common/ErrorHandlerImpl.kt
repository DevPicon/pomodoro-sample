package pe.devpicon.android.codelab.pomodoro.data.common

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseException
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncError
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import java.io.IOException

class ErrorHandlerImpl : ErrorHandler, SyncErrorHandler {
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

    override fun getSyncError(throwable: Throwable): SyncError {
        return when (throwable) {
            is IOException -> SyncError.NetworkError
            is DatabaseException -> databaseErrorCodeMapper(throwable)
            else -> {
                SyncError.GenericError(null, throwable.message ?: "")
            }
        }
    }

    private fun databaseErrorCodeMapper(throwable: DatabaseException): SyncError {
        return SyncError.NetworkError
    }
}
