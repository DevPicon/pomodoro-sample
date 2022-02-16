package pe.devpicon.android.codelab.pomodoro.data.local

import kotlinx.coroutines.flow.Flow
import pe.devpicon.android.codelab.pomodoro.data.local.dao.TaskDao
import pe.devpicon.android.codelab.pomodoro.data.local.entity.TaskEntity

class TaskLocalDataSourceImpl(private val taskDao: TaskDao) : TaskLocalDataSource {

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTask()
    }

    override suspend fun insetTask(entity: TaskEntity): Long {
        return taskDao.insert(entity)
    }

    override suspend fun getTaskById(id: Long): TaskEntity {
        return taskDao.getTaskById(id)
    }

    override suspend fun deleteTasks(idList: List<Long>) = taskDao.deleteTaskList(idList)

}
