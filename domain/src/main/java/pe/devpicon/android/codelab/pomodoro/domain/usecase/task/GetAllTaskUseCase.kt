package pe.devpicon.android.codelab.pomodoro.domain.usecase.task

import kotlinx.coroutines.flow.Flow
import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.FlowableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository

class GetAllTaskUseCase(
    private val repository: TaskRepository,
    errorHandler: ErrorHandler
) : FlowableUseCase<Unit, List<Task>>(errorHandler) {

    override fun execute(parameters: Unit): Flow<List<Task>> =
        repository.getAllTasks()
}
