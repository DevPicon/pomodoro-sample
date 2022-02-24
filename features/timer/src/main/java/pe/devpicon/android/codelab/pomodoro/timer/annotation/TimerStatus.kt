package pe.devpicon.android.codelab.pomodoro.timer.annotation

import androidx.annotation.IntDef
import pe.devpicon.android.codelab.pomodoro.timer.annotation.TimerStatus.Companion.PAUSE
import pe.devpicon.android.codelab.pomodoro.timer.annotation.TimerStatus.Companion.PLAY

@Target(
    AnnotationTarget.CLASS, AnnotationTarget.TYPE,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER
)
@IntDef(PLAY, PAUSE)
@Retention(AnnotationRetention.SOURCE)
annotation class TimerStatus {

    companion object {
        const val PLAY = 0
        const val PAUSE = 1
    }
}
