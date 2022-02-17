package pe.devpicon.android.codelab.pomodoro.data.local.database

import android.content.Context
import androidx.room.*
import pe.devpicon.android.codelab.pomodoro.data.local.dao.TaskDao
import pe.devpicon.android.codelab.pomodoro.data.local.entity.TaskEntity
import java.util.*

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PomodoroDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: PomodoroDatabase? = null

        private const val TASK_DATABASE_NAME = "task.db"

        fun getInstance(context: Context): PomodoroDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PomodoroDatabase::class.java, TASK_DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
