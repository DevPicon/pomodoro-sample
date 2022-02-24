package pe.devpicon.android.codelab.pomodoro.domain.repository

interface RandomRepository {
    suspend fun getRandomActivity(): String
}
