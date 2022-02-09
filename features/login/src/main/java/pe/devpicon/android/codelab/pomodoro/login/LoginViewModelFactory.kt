package pe.devpicon.android.codelab.pomodoro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignInUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignUpUseCase

class LoginViewModelFactory(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(signUpUseCase, signInUseCase) as T
    }
}
