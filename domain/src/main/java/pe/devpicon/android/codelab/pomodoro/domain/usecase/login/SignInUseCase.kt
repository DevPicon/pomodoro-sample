package pe.devpicon.android.codelab.pomodoro.domain.usecase.login

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.model.User
import pe.devpicon.android.codelab.pomodoro.domain.repository.LoginRepository

class SignInUseCase(
    private val repository: LoginRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<SignInUseCase.SignInParams, User>(errorHandler) {

    data class SignInParams(val email: String, val password: String)

    override suspend fun execute(parameters: SignInParams): User {
        return repository.signIn(parameters.email, parameters.password)
    }
}
