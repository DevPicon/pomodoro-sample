package pe.devpicon.android.codelab.pomodoro.domain_di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.devpicon.android.codelab.pomodoro.data.common.ErrorHandlerImpl
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.mapper.DomainMapper
import pe.devpicon.android.codelab.pomodoro.data.remote.LoginRemoteDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.repository.LoginRepositoryImpl
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignInUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.login.SignUpUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignInUseCase(): SignInUseCase =
        SignInUseCase(
            LoginRepositoryImpl(
                LoginRemoteDataSourceImpl(
                    FirebaseAuth.getInstance()
                ),
                UserLocalDataSourceImpl(),
                DomainMapper()
            ),
            ErrorHandlerImpl()
        )

    @Provides
    fun provideSignUpUseCase(): SignUpUseCase =
        SignUpUseCase(
            LoginRepositoryImpl(
                LoginRemoteDataSourceImpl(
                    FirebaseAuth.getInstance()
                ),
                UserLocalDataSourceImpl(),
                DomainMapper()
            ),
            ErrorHandlerImpl()
        )

}
