package pe.devpicon.android.codelab.pomodoro.timer

sealed class TimerScreenEvent {
    data class LoadData(val id: Long) : TimerScreenEvent()
    object OnPlayStopButtonClicked: TimerScreenEvent()
    object OnBackPressed: TimerScreenEvent()
}
