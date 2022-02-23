package pe.devpicon.android.codelab.pomodoro.timer

import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode
import pe.devpicon.android.codelab.pomodoro.timer.annotation.TimerStatus

sealed class TimerScreenState {
    object Initial : TimerScreenState()
    object Loading : TimerScreenState()
    class DataLoaded(
        val taskDetail: TimerDetail,
        val time: Long,
        val progress: Float,
        @TimerStatus val status: Int,
        @PomodoroMode val mode: Int
    ) : TimerScreenState()

}
