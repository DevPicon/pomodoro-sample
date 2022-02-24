package pe.devpicon.android.codelab.pomodoro.data.remote

import pe.devpicon.android.codelab.pomodoro.data.remote.response.RandomActivityResponse
import pe.devpicon.android.codelab.pomodoro.data.remote.retrofit.RandomService

class RandomRemoteDataSourceImpl(
    private val randomService: RandomService
) : RandomRemoteDataSource {
    override suspend fun getRandomActivity(): RandomActivityResponse {
        return randomService.getRandomActivity()
    }
}
