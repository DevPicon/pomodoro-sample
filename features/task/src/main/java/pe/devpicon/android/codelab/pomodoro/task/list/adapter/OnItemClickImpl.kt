package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator

class OnItemClickImpl(
    private val navigator: TaskNavigator
) : OnItemClick<TaskListItem> {
    override fun onItemSelectedClick(selectedSize: Int) {
        TODO("Not yet implemented")
    }

    override fun onLongPress(selectedSize: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(element: TaskListItem) {
        navigator.navigateOnToTimer(element.id, element.name)
    }
}
