package pe.devpicon.android.codelab.pomodoro.login

sealed interface LoginScreenState {
    object SignIn : LoginScreenState
    object SignUp : LoginScreenState
    object Loading : LoginScreenState
    data class Success(val data: String) : LoginScreenState
}
