package pe.devpicon.android.codelab.pomodoro.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "creationDate")
    val creationDate: Date,
    @ColumnInfo(name = "done_pomodoros")
    val donePomodoros: Int,
    @ColumnInfo(name = "estimated_pomodoros")
    val estimatedPomodoros: Int,
    @ColumnInfo(name = "short_breaks")
    val shortBreaks: Int,
    @ColumnInfo(name = "long_breaks")
    val longBreaks: Int,
    @ColumnInfo(name = "is_completed")
    val completed: Boolean
)
