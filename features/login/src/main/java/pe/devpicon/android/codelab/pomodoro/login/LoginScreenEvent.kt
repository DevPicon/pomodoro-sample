package pe.devpicon.android.codelab.pomodoro.login

sealed class LoginScreenEvent {
    data class OnTextChanged(
        val username: String,
        val password: String,
        val confirmPassword: String
    ) : LoginScreenEvent()

    object OnSecondaryButtonClicked : LoginScreenEvent()
    object OnMainButtonClicked : LoginScreenEvent()
}
