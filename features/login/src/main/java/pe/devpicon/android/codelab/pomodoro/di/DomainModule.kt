package pe.devpicon.android.codelab.pomodoro.di

import pe.devpicon.android.codelab.pomodoro.data.di.DataModule
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignInUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignUpUseCase

object DomainModule {

    val signInUseCase: SignInUseCase =
        SignInUseCase(
            DataModule.loginRepository,
            DataModule.errorHandler
        )

    val signUpUseCase: SignUpUseCase =
        SignUpUseCase(
            DataModule.loginRepository,
            DataModule.errorHandler
        )
}
