package pe.devpicon.android.codelab.pomodoro.data.local

import kotlinx.coroutines.flow.Flow
import pe.devpicon.android.codelab.pomodoro.data.local.entity.TaskEntity

interface TaskLocalDataSource {

    fun getAllTasks(): Flow<List<TaskEntity>>

    suspend fun insetTask(entity: TaskEntity): Long

    suspend fun getTaskById(id: Long): TaskEntity

    suspend fun deleteTasks(idList: List<Long>)
}
