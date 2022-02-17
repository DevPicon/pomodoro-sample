package pe.devpicon.android.codelab.pomodoro.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pe.devpicon.android.codelab.pomodoro.data.local.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskList: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM TASKS WHERE id IN (:list)")
    suspend fun deleteTaskList(list: List<Long>)

    @Query("SELECT * FROM tasks WHERE id is :id")
    suspend fun getTaskById(id: Long): TaskEntity

}
