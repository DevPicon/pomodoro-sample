package pe.devpicon.android.codelab.pomodoro.domain_di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.devpicon.android.codelab.pomodoro.data.common.ErrorHandlerImpl
import pe.devpicon.android.codelab.pomodoro.data.remote.RandomRemoteDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.remote.retrofit.RandomService
import pe.devpicon.android.codelab.pomodoro.data.remote.retrofit.retrofitClient
import pe.devpicon.android.codelab.pomodoro.data.repository.RandomRepositoryImpl
import pe.devpicon.android.codelab.pomodoro.domain.usecase.random.GetRandomActivityUseCase

@Module
@InstallIn(SingletonComponent::class)
object RandomDomainModule {

    @Provides
    fun providesGetRandomActivityUseCase(): GetRandomActivityUseCase =
        GetRandomActivityUseCase(
            repository = RandomRepositoryImpl(
                randomRemoteDataSource = RandomRemoteDataSourceImpl(
                    randomService = retrofitClient.create(RandomService::class.java)
                )
            ),
            errorHandler = ErrorHandlerImpl()
        )
}
