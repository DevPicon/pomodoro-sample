package pe.devpicon.android.codelab.pomodoro.domain.usecase.task

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<DeleteTaskUseCase.DeleteTaskUseCaseParams, Unit>(
    errorHandler
) {

    override suspend fun execute(parameters: DeleteTaskUseCaseParams) =
        repository.deleteTasks(parameters.idList)

    data class DeleteTaskUseCaseParams(val idList: List<Long>)
}
