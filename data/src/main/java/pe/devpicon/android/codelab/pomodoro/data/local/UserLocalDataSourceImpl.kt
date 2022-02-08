package pe.devpicon.android.codelab.pomodoro.data.local

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi

class UserLocalDataSourceImpl : UserLocalDataSource {

    private var user: UserApi? = null

    override fun setUser(user: UserApi) {
        this.user = user
    }

    override fun getUserId() = user?.id ?: ""

    override fun getEmail() = user?.email ?: ""

    override fun clear() {
        this.user = null
    }
}
