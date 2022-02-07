package pe.devpicon.android.codelab.pomodoro.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.devpicon.android.codelab.pomodoro.core.Event

class LoginViewModel : ViewModel() {

    private var username: String = ""
    private var password: String = ""
    private var confirmPassword = ""

    private var loginMode: LoginScreenMode = LoginScreenMode.SignInMode

    private val _screenState: MutableLiveData<Event<LoginScreenState>> =
        MutableLiveData(Event(LoginScreenState.SignIn))
    val screenState: LiveData<Event<LoginScreenState>>
        get() = _screenState

    fun postEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnMainButtonClicked -> {
                when (loginMode) {
                    is LoginScreenMode.SignInMode -> {
                        startSignIn()
                    }
                    is LoginScreenMode.SignUpMode -> {
                        startSignUp()
                    }
                }
            }
            is LoginScreenEvent.OnSecondaryButtonClicked -> toggleMode()
            is LoginScreenEvent.OnTextChanged -> {
                username = event.username
                password = event.password
                confirmPassword = event.confirmPassword
            }
        }
    }

    private fun startSignUp() {
        viewModelScope.launch {
            _screenState.value = Event(LoginScreenState.Loading)
            TODO("Not yet implemented")
        }
    }

    private fun startSignIn() {
        viewModelScope.launch {
            _screenState.value = Event(LoginScreenState.Loading)
            TODO("Not yet implemented")
        }
    }

    private fun toggleMode() {
        when (loginMode) {
            is LoginScreenMode.SignInMode -> {
                loginMode = LoginScreenMode.SignUpMode
                _screenState.value = Event(LoginScreenState.SignUp)
            }
            is LoginScreenMode.SignUpMode -> {
                loginMode = LoginScreenMode.SignInMode
                _screenState.value = Event(LoginScreenState.SignIn)
            }
        }
    }

}
