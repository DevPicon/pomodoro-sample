package pe.devpicon.android.codelab.pomodoro.data.local

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi

interface UserLocalDataSource {

    fun setUser(user: UserApi)

    fun getUserId(): String

    fun getEmail(): String

    fun clear()
}
