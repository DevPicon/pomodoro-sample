package pe.devpicon.android.codelab.pomodoro.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.UserApi

class LoginRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth
) : LoginRemoteDataSource {
    override suspend fun signUp(email: String, password: String): UserApi {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val tokenResult = firebaseAuth.getAccessToken(true).await()

        return UserApi(
            email = authResult.user?.email ?: "",
            id = authResult.user?.uid ?: "",
            token = tokenResult.token ?: ""
        )
    }

    override suspend fun signIn(email: String, password: String): UserApi {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val tokenResult = firebaseAuth.getAccessToken(true).await()

        return UserApi(
            email = authResult.user?.email ?: "",
            id = authResult.user?.uid ?: "",
            token = tokenResult.token ?: ""
        )
    }
}
