package pe.devpicon.android.codelab.pomodoro.domain.common

sealed class Error {
    data class GenericError(val code: Int? = null, val message: String) : Error()
    data class UserEmailError(val message: String) : Error()
    data class UserPasswordError(val message: String) : Error()
    object NetworkError : Error()
}
