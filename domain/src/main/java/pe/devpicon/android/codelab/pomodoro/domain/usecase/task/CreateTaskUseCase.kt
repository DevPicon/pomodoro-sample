package pe.devpicon.android.codelab.pomodoro.domain.usecase.task

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.repository.TaskRepository
import java.util.*

class CreateTaskUseCase(
    private val repository: TaskRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<CreateTaskUseCase.CreateTaskParams, Long>(errorHandler) {

    override suspend fun execute(parameters: CreateTaskParams): Long {
        val task = Task(
            name = parameters.name,
            estimatedPomodoros = parameters.estimatedPomodoros,
            donePomodoros = parameters.donePomodoros,
            shortBreaks = calculateShortBreaks(parameters.estimatedPomodoros),
            longBreaks = calculateLongBreaks(parameters.estimatedPomodoros),
            creationDate = parameters.creationDate,
            completed = false
        )
        return repository.insertTask(task)
    }

    private fun calculateShortBreaks(estimatedPomodoros: Int) = estimatedPomodoros * 4

    private fun calculateLongBreaks(estimatedPomodoros: Int) = estimatedPomodoros / 4

    data class CreateTaskParams(
        val name: String,
        val creationDate: Date,
        val donePomodoros: Int,
        val estimatedPomodoros: Int,
        val completed: Boolean
    )
}
