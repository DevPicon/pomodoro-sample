package pe.devpicon.android.codelab.pomodoro.data.remote

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi

const val TASK_MAIN_URL = "tasks"

class TaskRemoteDataSourceImpl(
    private val database: DatabaseReference
) : TaskRemoteDataSource {
    override suspend fun insertTask(userId: String, taskApi: TaskApi) {
        database.child(TASK_MAIN_URL)
            .child(userId)
            .child(taskApi.id.toString())
            .setValue(taskApi)
            .await()
    }
}
