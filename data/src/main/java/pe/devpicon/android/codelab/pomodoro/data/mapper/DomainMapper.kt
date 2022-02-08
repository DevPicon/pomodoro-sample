package pe.devpicon.android.codelab.pomodoro.data.mapper

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi
import pe.devpicon.android.codelab.pomodoro.domain.model.User

class DomainMapper {
    fun fromUserApiToUser(result: UserApi): User {
        return User(
            email = result.email,
            id = result.id,
            token = result.token
        )
    }

}
