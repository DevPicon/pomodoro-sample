package pe.devpicon.android.codelab.pomodoro.data.remote.firebase

import java.util.*

data class TaskApi (
    val id: Long,
    val name: String,
    val creationDate: Date,
    val donePomodoros: Int,
    val estimatedPomodoros: Int,
    val shortBreaks: Int,
    val longBreaks: Int,
    val completed: Boolean
)
