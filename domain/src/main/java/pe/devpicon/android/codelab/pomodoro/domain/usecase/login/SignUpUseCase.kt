package pe.devpicon.android.codelab.pomodoro.domain.usecase.login

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.model.User
import pe.devpicon.android.codelab.pomodoro.domain.repository.LoginRepository

class SignUpUseCase(
    private val repository: LoginRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<SignUpUseCase.SignUpParams, User>(errorHandler) {

    data class SignUpParams(val email: String, val password: String)

    override suspend fun execute(parameters: SignUpParams): User {
        return repository.signUp(parameters.email, parameters.password)
    }


}
