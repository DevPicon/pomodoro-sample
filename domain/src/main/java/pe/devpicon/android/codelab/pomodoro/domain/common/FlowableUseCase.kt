package pe.devpicon.android.codelab.pomodoro.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowableUseCase<in P, out R>(
    private val errorHandler: ErrorHandler,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    operator fun invoke(parameters: P): Flow<ResultWrapper<R>> {
        return flow {
            try {
                execute(parameters).collect {
                    emit(ResultWrapper.Success(it))
                }
            } catch (throwable: Throwable) {
                emit(ResultWrapper.Failure(errorHandler.getError(throwable)))
            }
        }.flowOn(coroutineDispatcher)
    }

    protected abstract fun execute(parameters: P): Flow<R>

}
