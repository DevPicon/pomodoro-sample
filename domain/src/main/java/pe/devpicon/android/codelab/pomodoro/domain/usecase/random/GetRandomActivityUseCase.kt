package pe.devpicon.android.codelab.pomodoro.domain.usecase.random

import pe.devpicon.android.codelab.pomodoro.domain.common.ErrorHandler
import pe.devpicon.android.codelab.pomodoro.domain.common.SuspendableUseCase
import pe.devpicon.android.codelab.pomodoro.domain.repository.RandomRepository

class GetRandomActivityUseCase(
    private val repository: RandomRepository,
    errorHandler: ErrorHandler
) : SuspendableUseCase<Unit, String>(errorHandler) {
    override suspend fun execute(parameters: Unit): String {
        return repository.getRandomActivity()
    }
}
