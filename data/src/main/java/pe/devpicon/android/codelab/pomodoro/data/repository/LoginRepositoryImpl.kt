package pe.devpicon.android.codelab.pomodoro.data.repository

import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.mapper.DomainMapper
import pe.devpicon.android.codelab.pomodoro.data.remote.LoginRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi
import pe.devpicon.android.codelab.pomodoro.domain.model.User
import pe.devpicon.android.codelab.pomodoro.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val domainMapper: DomainMapper
) : LoginRepository {

    override suspend fun signUp(email: String, password: String): User {
        val result: UserApi = loginRemoteDataSource.signUp(email, password)
        saveUser(result)
        return domainMapper.fromUserApiToUser(result)
    }

    override suspend fun signIn(email: String, password: String): User {
        val result: UserApi = loginRemoteDataSource.signIn(email, password)
        saveUser(result)
        return domainMapper.fromUserApiToUser(result)
    }

    private fun saveUser(value: UserApi) {
        userLocalDataSource.setUser(value)
    }
}
