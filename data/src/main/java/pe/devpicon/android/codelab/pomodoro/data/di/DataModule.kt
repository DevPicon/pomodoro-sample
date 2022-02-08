package pe.devpicon.android.codelab.pomodoro.data.di

import com.google.firebase.auth.FirebaseAuth
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.mapper.DomainMapper
import pe.devpicon.android.codelab.pomodoro.data.remote.LoginRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.LoginRemoteDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.repository.LoginRepositoryImpl
import pe.devpicon.android.codelab.pomodoro.domain.repository.LoginRepository

object DataModule {

    private val userLocalDataSource: UserLocalDataSource = UserLocalDataSourceImpl()

    private val loginRemoteDataSource: LoginRemoteDataSource = LoginRemoteDataSourceImpl(
        FirebaseAuth.getInstance()
    )

    val loginRepository: LoginRepository = LoginRepositoryImpl(
        loginRemoteDataSource = loginRemoteDataSource,
        userLocalDataSource = userLocalDataSource,
        domainMapper = DomainMapper()
    )

}
