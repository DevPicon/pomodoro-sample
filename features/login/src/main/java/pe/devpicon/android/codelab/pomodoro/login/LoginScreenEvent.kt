package pe.devpicon.android.codelab.pomodoro.login

sealed class LoginScreenEvent {
    data class OnSubmit(
        val username: String,
        val password: String,
        val confirmPassword: String
    ) : LoginScreenEvent()

    object OnSignUp : LoginScreenEvent()
    object OnSignIn : LoginScreenEvent()
}
