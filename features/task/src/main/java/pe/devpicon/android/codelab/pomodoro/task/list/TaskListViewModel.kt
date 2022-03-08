package pe.devpicon.android.codelab.pomodoro.task.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pe.devpicon.android.codelab.pomodoro.core.Event
import pe.devpicon.android.codelab.pomodoro.core.SnackBarError
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ResultWrapper
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.DeleteTaskUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.GetAllTaskUseCase
import pe.devpicon.android.codelab.pomodoro.task.ViewMapper
import pe.devpicon.android.codelab.pomodoro.task.list.adapter.TaskListItem
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel
@Inject constructor(
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val viewMapper: ViewMapper

) : ViewModel() {

    private var _taskList: List<TaskListItem> = emptyList()

    private val _screenState: MutableLiveData<Event<TaskListScreenState>> =
        MutableLiveData(Event(TaskListScreenState.Initial))
    val screenState: LiveData<Event<TaskListScreenState>>
        get() = _screenState

    private val _error: MutableLiveData<Event<SnackBarError>> = MutableLiveData()
    val error: LiveData<Event<SnackBarError>>
        get() = _error

    private val _navigationToCreateTask: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigationToCreateTask: LiveData<Event<Boolean>>
        get() = _navigationToCreateTask

    fun postEvent(event: TaskListScreenEvent) {
        when (event) {
            TaskListScreenEvent.Load -> loadTaskList()
            TaskListScreenEvent.AddTaskPressed -> {
                _navigationToCreateTask.value = Event(true)
            }
            is TaskListScreenEvent.OnDeleteActionItemClicked -> {
                viewModelScope.launch {
                    deleteTaskUseCase(DeleteTaskUseCase.DeleteTaskUseCaseParams(
                        event.selectedItemList.map { it.id }
                    ))
                }

            }
            TaskListScreenEvent.OnFinishActionMode -> {
                _screenState.value = Event(TaskListScreenState.HideActionMode)
            }
            TaskListScreenEvent.OnStartActionMode -> {
                _screenState.value = Event(TaskListScreenState.ShowActionMode)
            }
        }
    }

    private fun loadTaskList() {
        subscribeFlow(
            getAllTaskUseCase.invoke(Unit)
                .onStart {
                    _screenState.value = Event(TaskListScreenState.Loading)
                }.onEach { result: ResultWrapper<List<Task>> ->
                    when (result) {
                        is ResultWrapper.Success<List<Task>> -> {
                            manageResult(result.value)
                        }
                        is ResultWrapper.Failure -> {
                            when (val error = result.error) {
                                Error.NetworkError -> showGenericError("Check internet connexion")
                                is Error.GenericError -> showGenericError(error.message)
                                is Error.UserEmailError -> showGenericError(error.message)
                                is Error.UserPasswordError -> showGenericError(error.message)
                            }
                        }
                    }
                }
        )
    }

    private fun showGenericError(message: String) {
        _error.value =
            Event(SnackBarError(message))
    }

    private fun manageResult(taskList: List<Task>) {
        _screenState.value = if (taskList.isEmpty()) {
            Event(TaskListScreenState.EmptyState)
        } else {
            viewMapper.fromModelToView(taskList)
                .let {
                    setList(it)
                    Event(TaskListScreenState.DataLoaded(it))
                }
        }
    }


    // ---
    private fun subscribeFlow(flow: Flow<Any>) {
        flow.onStart {
        }.onCompletion {
        }.flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun setList(task: List<TaskListItem>) {
        _taskList = task
    }
}
