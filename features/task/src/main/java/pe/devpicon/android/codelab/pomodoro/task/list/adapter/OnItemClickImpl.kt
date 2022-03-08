package pe.devpicon.android.codelab.pomodoro.task.list.adapter

import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator

class OnItemClickImpl(
    private val navigator: TaskNavigator,
    private val onStartActionMode: (selectedItemCount:Int) -> Unit,
    private val onItemClicked: (selectedItemCount:Int) -> Unit
) : OnItemClick<TaskListItem> {
    override fun onItemSelectedClick(selectedSize: Int) {
        onItemClicked.invoke(selectedSize)
    }

    override fun onLongPress(selectedSize: Int) {
        onStartActionMode.invoke(selectedSize)
    }

    override fun onItemClick(element: TaskListItem) {
        navigator.navigateOnToTimer(element.id, element.name)
    }
}
