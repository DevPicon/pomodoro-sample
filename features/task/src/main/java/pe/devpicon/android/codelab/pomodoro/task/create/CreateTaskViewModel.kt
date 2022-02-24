package pe.devpicon.android.codelab.pomodoro.task.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.devpicon.android.codelab.pomodoro.core.Event
import pe.devpicon.android.codelab.pomodoro.core.SnackBarError
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ResultWrapper
import pe.devpicon.android.codelab.pomodoro.domain.usecase.random.GetRandomActivityUseCase
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.CreateTaskUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel
@Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val getRandomActivityUseCase: GetRandomActivityUseCase
) : ViewModel() {
    private var taskName: String = ""
    private var taskPomodoroQty: Int = 1

    private val _screenState: MutableLiveData<Event<CreateTaskScreenState>> =
        MutableLiveData(Event(CreateTaskScreenState.Start()))
    val screenState: LiveData<Event<CreateTaskScreenState>>
        get() = _screenState

    private val _error: MutableLiveData<Event<SnackBarError>> = MutableLiveData()
    val error: LiveData<Event<SnackBarError>>
        get() = _error

    private val _navigateToTaskList: MutableLiveData<Event<Boolean>> =
        MutableLiveData()
    val navigateToTaskList: LiveData<Event<Boolean>>
        get() = _navigateToTaskList

    fun postEvent(event: CreateTaskScreenEvent) {
        when (event) {
            is CreateTaskScreenEvent.EditingTask -> {
                setTaskName(event.name)
                setPomodoroCounter(event.estimatedPomodoros)
            }
            is CreateTaskScreenEvent.SaveTask -> {
                save()
            }
            CreateTaskScreenEvent.OnPressRandomButton -> getRandomActivity()
        }
    }

    private fun getRandomActivity() {
        val startState = getStartState()
        startState?.let {
            val showInlineLoader = it.copy(showInlineLoader = true)
            _screenState.value = Event(showInlineLoader)
            viewModelScope.launch {
                when (val result = getRandomActivityUseCase(Unit)) {
                    is ResultWrapper.Failure -> {
                        onFailure(result.error)
                        _screenState.value = Event(it.copy(showInlineLoader = false))
                    }
                    is ResultWrapper.Success -> {
                        setTaskName(result.value)
                        val newContent = it.copy(name = result.value, showInlineLoader = false)
                        _screenState.value = Event(newContent)
                    }
                }
            }
        } ?: run {
            onFailure(Error.GenericError(message = "Invalid state"))
        }
    }

    private fun getStartState(): CreateTaskScreenState.Start? {
        var currentState: CreateTaskScreenState.Start? = null
        if (_screenState.value?.peekContent() is CreateTaskScreenState.Start) {
            currentState = _screenState.value?.peekContent() as CreateTaskScreenState.Start
        }
        return currentState
    }

    private fun save() {
        viewModelScope.launch {
            _screenState.value = Event(CreateTaskScreenState.Loading)

            val result = createTaskUseCase(
                CreateTaskUseCase.CreateTaskParams(
                    name = taskName,
                    estimatedPomodoros = taskPomodoroQty,
                    donePomodoros = 0,
                    creationDate = getCurrentDate(),
                    completed = false
                )
            )

            when (result) {
                is ResultWrapper.Failure -> {
                    onFailure(result.error)
                    _screenState.value =
                        Event(CreateTaskScreenState.Start(taskName, taskPomodoroQty))
                }
                is ResultWrapper.Success<Long> -> {
                    _screenState.value = Event(CreateTaskScreenState.Success)
                    _navigateToTaskList.value = Event(true)
                }
            }
        }
    }

    private fun onFailure(error: Error) {
        when (error) {
            is Error.GenericError -> showGenericError(error.message)
            Error.NetworkError -> showGenericError("Check internet connexion")
            is Error.UserEmailError -> showGenericError(error.message)
            is Error.UserPasswordError -> showGenericError(error.message)
        }
    }

    private fun showGenericError(error: String) {
        _error.value =
            Event(SnackBarError(error))
    }

    private fun getCurrentDate(): Date =
        Calendar.getInstance().time

    private fun setTaskName(name: String) {
        taskName = name
    }

    private fun setPomodoroCounter(pomodoros: Int) {
        taskPomodoroQty = pomodoros
    }
}
