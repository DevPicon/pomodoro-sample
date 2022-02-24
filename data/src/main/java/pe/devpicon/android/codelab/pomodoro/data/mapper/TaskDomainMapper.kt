package pe.devpicon.android.codelab.pomodoro.data.mapper

import pe.devpicon.android.codelab.pomodoro.data.local.entity.TaskEntity
import pe.devpicon.android.codelab.pomodoro.data.remote.firebase.TaskApi
import pe.devpicon.android.codelab.pomodoro.domain.model.Task

class TaskDomainMapper {

    fun fromTaskEntitytoDomainModel(taskEntity: TaskEntity) = with(taskEntity) {
        Task(
            id,
            name,
            creationDate,
            donePomodoros,
            estimatedPomodoros,
            shortBreaks,
            longBreaks,
            completed
        )
    }

    fun fromTasktoDataModel(task: Task) = with(task) {
        TaskEntity(
            id,
            name,
            creationDate,
            donePomodoros,
            estimatedPomodoros,
            shortBreaks,
            longBreaks,
            completed
        )
    }

    fun fromTaskToRemoteModel(task: Task) = with(task) {
        TaskApi(
            id ?: -1,
            name,
            creationDate,
            donePomodoros,
            estimatedPomodoros,
            shortBreaks,
            longBreaks,
            completed
        )
    }

}
