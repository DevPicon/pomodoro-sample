package pe.devpicon.android.codelab.pomodoro.timer.annotation

import androidx.annotation.IntDef
import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode.Companion.LONG_BREAK
import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode.Companion.POMODORO
import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode.Companion.SHORT_BREAK

@Target(
    AnnotationTarget.CLASS, AnnotationTarget.TYPE,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER
)
@IntDef(POMODORO, SHORT_BREAK, LONG_BREAK)
@Retention(AnnotationRetention.SOURCE)
annotation class PomodoroMode {

    companion object {
        const val POMODORO = 0
        const val SHORT_BREAK = 1
        const val LONG_BREAK = 2

    }
}
