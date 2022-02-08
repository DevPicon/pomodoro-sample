package pe.devpicon.android.codelab.pomodoro.domain.repository

import pe.devpicon.android.codelab.pomodoro.domain.model.User

interface LoginRepository {
    suspend fun signUp(email: String, password: String): User
    suspend fun signIn(email: String, password: String): User
}
