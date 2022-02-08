package pe.devpicon.android.codelab.pomodoro.domain.repository

import pe.devpicon.android.codelab.pomodoro.domain.model.User
import kotlin.random.Random

const val FAKE_TOKEN = "this is a fake token"

class FakeLoginRepository : LoginRepository {
    override suspend fun signUp(email: String, password: String): User {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            return User(email, Random(24).nextInt().toString(), FAKE_TOKEN)
        } else {
            throw RuntimeException("Email and password should not be empty")
        }
    }

    override suspend fun signIn(email: String, password: String): User {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            return User(email, Random(24).nextInt().toString(), FAKE_TOKEN)
        } else {
            throw RuntimeException("Email and password should not be empty")
        }
    }
}
