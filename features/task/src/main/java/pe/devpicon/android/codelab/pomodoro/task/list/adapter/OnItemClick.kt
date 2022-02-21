package pe.devpicon.android.codelab.pomodoro.task.list.adapter

interface OnItemClick<M> {
    fun onItemSelectedClick(selectedSize: Int)
    fun onLongPress(selectedSize: Int)
    fun onItemClick(element: M)
}
