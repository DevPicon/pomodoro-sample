package pe.devpicon.android.codelab.pomodoro.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import pe.devpicon.android.codelab.pomodoro.data.local.TaskLocalDataSource
import pe.devpicon.android.codelab.pomodoro.data.mapper.TaskDomainMapper
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val localDataSource: TaskLocalDataSource,
    private val taskMapper: TaskDomainMapper
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
        return localDataSource.insetTask(
            taskMapper.fromTasktoDataModel(task)
        )
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
