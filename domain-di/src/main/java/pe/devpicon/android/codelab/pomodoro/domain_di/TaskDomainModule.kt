package pe.devpicon.android.codelab.pomodoro.domain_di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.devpicon.android.codelab.pomodoro.data.common.ErrorHandlerImpl
import pe.devpicon.android.codelab.pomodoro.data.local.TaskLocalDataSourceImpl
import pe.devpicon.android.codelab.pomodoro.data.local.database.PomodoroDatabase
import pe.devpicon.android.codelab.pomodoro.data.mapper.TaskDomainMapper
import pe.devpicon.android.codelab.pomodoro.data.repository.TaskRepositoryImpl
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncManager
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.CreateTaskUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.DeleteTaskUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.GetAllTaskUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.GetTaskByIdUseCase
import pe.devpicon.android.codelab.pomodoro.sync.SyncManagerImpl

@Module
@InstallIn(SingletonComponent::class)
object TaskDomainModule {

    private lateinit var database: PomodoroDatabase
    private lateinit var syncManager: SyncManager

    @Provides
    fun provideGetTaskByIdUseCase(@ApplicationContext context: Context): GetTaskByIdUseCase =
        GetTaskByIdUseCase(
            repository = provideTaskRepository(context),
            errorHandler = ErrorHandlerImpl()
        )

    @Provides
    fun provideGetAllTaskUseCase(@ApplicationContext context: Context): GetAllTaskUseCase =
        GetAllTaskUseCase(
            repository = provideTaskRepository(context),
            errorHandler = ErrorHandlerImpl()
        )

    @Provides
    fun provideCreateTaskUseCase(@ApplicationContext context: Context): CreateTaskUseCase =
        CreateTaskUseCase(
            repository = provideTaskRepository(context),
            errorHandler = ErrorHandlerImpl()
        )

    @Provides
    fun provideDeleteTaskUseCase(@ApplicationContext context: Context): DeleteTaskUseCase =
        DeleteTaskUseCase(
            repository = provideTaskRepository(context),
            errorHandler = ErrorHandlerImpl()
        )

    private fun provideTaskRepository(context: Context) = TaskRepositoryImpl(
        localDataSource = TaskLocalDataSourceImpl(
            taskDao = privatelyProvideLocalDatabase(context).taskDao()
        ),
        taskMapper = TaskDomainMapper(),
        syncManager = provideSyncManager(context)
    )

    @Synchronized
    private fun provideSyncManager(context: Context): SyncManager {
        if (!::syncManager.isInitialized) {
            syncManager = SyncManagerImpl(
                context
            )
        }
        return syncManager
    }

    @Synchronized
    private fun privatelyProvideLocalDatabase(context: Context): PomodoroDatabase {
        if (!::database.isInitialized) {
            database = PomodoroDatabase.getInstance(context)
        }
        return database
    }
}
