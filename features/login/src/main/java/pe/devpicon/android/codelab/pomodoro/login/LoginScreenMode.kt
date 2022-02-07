package pe.devpicon.android.codelab.pomodoro.login

interface LoginScreenMode {
    object SignInMode : LoginScreenMode
    object SignUpMode : LoginScreenMode
}
