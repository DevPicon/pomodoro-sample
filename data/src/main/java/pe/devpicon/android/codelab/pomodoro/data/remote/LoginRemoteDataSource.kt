package pe.devpicon.android.codelab.pomodoro.data.remote

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi

interface LoginRemoteDataSource {
    suspend fun signUp(email: String, password: String): UserApi
    suspend fun signIn(email: String, password: String): UserApi
}
