package pe.devpicon.android.codelab.pomodoro.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SuspendableUseCase<in P, out R>(
    private val errorHandler: ErrorHandler,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(parameters: P): ResultWrapper {
        return withContext(coroutineDispatcher) {
            try {
                ResultWrapper.Success<R>(execute(parameters))
            } catch (throwable: Throwable) {
                ResultWrapper.Failure(errorHandler.getError(throwable))
            }
        }
    }

    protected abstract fun execute(parameters: P): R
}
