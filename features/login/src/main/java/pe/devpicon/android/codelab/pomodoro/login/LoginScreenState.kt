package pe.devpicon.android.codelab.pomodoro.login

sealed interface LoginScreenState {
    object SignIn : LoginScreenState
    object SignUp : LoginScreenState
    object Loading : LoginScreenState
}
