package pe.devpicon.android.codelab.pomodoro.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.devpicon.android.codelab.pomodoro.core.Event

class LoginViewModel : ViewModel() {

    private var username: String = ""
    private var password: String = ""
    private var confirmPassword = ""

    private val _screenState: MutableLiveData<Event<LoginScreenState>> =
        MutableLiveData(Event(LoginScreenState.SignIn))
    val screenState: LiveData<Event<LoginScreenState>>
        get() = _screenState

    fun postEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnMainButtonClicked -> TODO()
            is LoginScreenEvent.OnSecondaryButtonClicked -> TODO()
            is LoginScreenEvent.OnTextChanged -> {
                username = event.username
                password = event.password
                confirmPassword = event.confirmPassword
            }
        }
    }

}
