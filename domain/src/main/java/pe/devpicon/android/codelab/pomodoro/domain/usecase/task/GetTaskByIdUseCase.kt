package pe.devpicon.android.codelab.pomodoro.domain.usecase.task

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<GetTaskByIdUseCase.GetTaskByIdParams, Task>(errorHandler = errorHandler) {

    data class GetTaskByIdParams(val taskId: Long)

    override suspend fun execute(parameters: GetTaskByIdParams): Task {
        return repository.getTaskById(id = parameters.taskId)
    }

}
