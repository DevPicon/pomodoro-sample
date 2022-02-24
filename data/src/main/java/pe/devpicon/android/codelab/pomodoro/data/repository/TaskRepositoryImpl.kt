package pe.devpicon.android.codelab.pomodoro.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import pe.devpicon.android.codelab.pomodoro.data.local.TaskLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.mapper.TaskDomainMapper
import pe.devpicon.android.codelab.pomodoro.data.sync.SyncManager
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val localDataSource: TaskLocalDataSource,
    private val taskMapper: TaskDomainMapper,
    private val syncManager: SyncManager
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> {
        return localDataSource.getAllTasks()
            .transform {
                emit(it.map { entity ->
                    taskMapper.fromTaskEntitytoDomainModel(entity)
                })
            }
    }

    override suspend fun insertTask(task: Task): Long {
        val result = localDataSource.insetTask(
            taskMapper.fromTasktoDataModel(task)
        )
        task.id = result
        syncManager.performSyncInsertion(taskMapper.fromTaskToRemoteModel(task))
        return result
    }

    override suspend fun deleteTasks(idList: List<Long>) {
        localDataSource.deleteTasks(idList)
    }

    override suspend fun getTaskById(id: Long): Task {
        return taskMapper.fromTaskEntitytoDomainModel(
            localDataSource.getTaskById(id)
        )
    }
}
