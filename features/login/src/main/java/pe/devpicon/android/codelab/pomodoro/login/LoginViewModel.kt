package pe.devpicon.android.codelab.pomodoro.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.devpicon.android.codelab.pomodoro.core.Event
import pe.devpicon.android.codelab.pomodoro.core.SnackBarError
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ResultWrapper
import pe.devpicon.android.codelab.pomodoro.domain.model.User
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignInUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignUpUseCase

class LoginViewModel : ViewModel() {

    private var username: String = ""
    private var password: String = ""
    private var confirmPassword = ""

    private var loginMode: LoginScreenMode = LoginScreenMode.SignInMode

    private val _screenState: MutableLiveData<Event<LoginScreenState>> =
        MutableLiveData(Event(LoginScreenState.SignIn))
    val screenState: LiveData<Event<LoginScreenState>>
        get() = _screenState

    private val _error: MutableLiveData<Event<SnackBarError>> = MutableLiveData()
    val error: LiveData<Event<SnackBarError>>
        get() = _error

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
            val result = signUpUseCase(
                SignUpUseCase.SignUpParams(
                    email = username,
                    password = password
                )
            )
            when (result) {
                is ResultWrapper.Failure -> {
                    _screenState.value = Event(LoginScreenState.SignUp)
                    onFailure(result.error)
                }
                is ResultWrapper.Success<*> -> {
                    onSignUpSuccess(result.value as User)
                }
            }

        }
    }

    private fun onFailure(error: Error) {
        when (error) {
            is Error.GenericError -> showGenericError(error.message)
            Error.NetworkError -> showGenericError("Check internet connexion")
            is Error.UserEmailError -> showGenericError(error.message)
            is Error.UserPasswordError -> showGenericError(error.message)
        }

    }

    private fun showGenericError(error: String) {
        _error.value =
            Event(SnackBarError(error))
    }

    private fun onSignUpSuccess(user: User) {
        _screenState.value =
            Event(LoginScreenState.Success("Generated token signup: ${user.token}"))
    }

    private fun onSignInSuccess(user: User) {
        _screenState.value =
            Event(LoginScreenState.Success("Generated token signin: ${user.token}"))
    }

    private fun startSignIn() {
        viewModelScope.launch {
            _screenState.value = Event(LoginScreenState.Loading)
            val result = signInUseCase(
                SignInUseCase.SignInParams(
                    email = username,
                    password = password
                )
            )
            when (result) {
                is ResultWrapper.Failure -> {
                    _screenState.value = Event(LoginScreenState.SignIn)
                    onFailure(result.error)
                }
                is ResultWrapper.Success<*> -> {
                    onSignInSuccess(result.value as User)
                }
            }
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
