package pe.devpicon.android.codelab.pomodoro.task.list

import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import pe.devpicon.android.codelab.pomodoro.task.R

class TaskListActionModeCallback(
    private val listener: ActionModeCallbackEventListener
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater: MenuInflater = mode.menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        listener.onShowActionMode()
        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                listener.onDeleteActionItemClicked()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        listener.onFinishActionMode()
    }

    interface ActionModeCallbackEventListener {
        fun onDeleteActionItemClicked()
        fun onShowActionMode()
        fun onFinishActionMode()
    }
}
