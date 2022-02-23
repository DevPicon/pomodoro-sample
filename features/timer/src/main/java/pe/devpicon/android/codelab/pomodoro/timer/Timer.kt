package pe.devpicon.android.codelab.pomodoro.timer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
fun getTimer(timeInMillis: Long, delayTime: Long): Flow<Long> = flow {
    var time = timeInMillis

    do {
        emit(time)
        time -= delayTime
        delay(delayTime)
    } while (time >= 0)
}.flowOn(Dispatchers.IO)
