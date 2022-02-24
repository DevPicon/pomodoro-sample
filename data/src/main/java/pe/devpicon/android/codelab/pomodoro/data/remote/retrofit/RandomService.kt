package pe.devpicon.android.codelab.pomodoro.data.remote.retrofit

import pe.devpicon.android.codelab.pomodoro.data.remote.common.RandomActivityUrl.RANDOM_ACTIVITY_URL
import pe.devpicon.android.codelab.pomodoro.data.remote.response.RandomActivityResponse
import retrofit2.http.GET

interface RandomService {
    @GET(RANDOM_ACTIVITY_URL)
    suspend fun getRandomActivity(): RandomActivityResponse
}
