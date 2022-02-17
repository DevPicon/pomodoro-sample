package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import java.util.*

data class TaskListItem(
    val id: Long,
    val name: String,
    val creationDate: Date,
    val estimatedPomodoros: String,
    val completed: Boolean
)
