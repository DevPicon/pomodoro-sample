package pe.devpicon.android.codelab.pomodoro.domain_di

import androidx.work.DelegatingWorkerFactory
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.devpicon.android.codelab.pomodoro.data.common.ErrorHandlerImpl
import pe.devpicon.android.codelab.pomodoro.data.local.UserLocalDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.remote.TaskRemoteDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.sync.workmanager.TaskWorkerFactory

@Module
@InstallIn(SingletonComponent::class)
object WorkerManagerModule {

    @Provides
    fun providesWorkerFactory(): DelegatingWorkerFactory {
        val workerFactory = DelegatingWorkerFactory()
        workerFactory.addFactory(
            TaskWorkerFactory(
                userLocalDataSource = UserLocalDataSourceImpl(),
                taskRemoteDataSource = TaskRemoteDataSourceImpl(
                    Firebase.database.reference
                ),
                errorHandler = ErrorHandlerImpl()
            )
        )
        return workerFactory
    }


}
