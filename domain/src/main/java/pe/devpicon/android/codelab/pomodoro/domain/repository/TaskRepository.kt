package pe.devpicon.android.codelab.pomodoro.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.devpicon.android.codelab.pomodoro.domain.model.Task

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task): Long

    suspend fun deleteTasks(idList: List<Long>)

}
