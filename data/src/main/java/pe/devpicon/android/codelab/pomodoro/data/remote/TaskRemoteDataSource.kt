package pe.devpicon.android.codelab.pomodoro.data.remote

import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi

interface TaskRemoteDataSource {
    suspend fun insertTask(userId: String, taskApi: TaskApi)
}
