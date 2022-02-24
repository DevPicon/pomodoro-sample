package pe.devpicon.android.codelab.pomodoro.data.remote

import pe.devpicon.android.codelab.pomodoro.data.remote.response.RandomActivityResponse

interface RandomRemoteDataSource {
    suspend fun getRandomActivity():RandomActivityResponse
}
