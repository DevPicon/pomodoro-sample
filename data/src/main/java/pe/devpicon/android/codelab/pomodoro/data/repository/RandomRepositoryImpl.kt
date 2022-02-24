package pe.devpicon.android.codelab.pomodoro.data.repository

import pe.devpicon.android.codelab.pomodoro.data.remote.RandomRemoteDataSource
import pe.devpicon.android.codelab.pomodoro.domain.repository.RandomRepository

class RandomRepositoryImpl(
    private val randomRemoteDataSource: RandomRemoteDataSource
) : RandomRepository {
    override suspend fun getRandomActivity(): String {
        return randomRemoteDataSource.getRandomActivity().activity
    }
}
