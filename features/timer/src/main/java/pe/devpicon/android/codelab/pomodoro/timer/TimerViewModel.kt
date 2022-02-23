package pe.devpicon.android.codelab.pomodoro.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pe.devpicon.android.codelab.pomodoro.core.Event
import pe.devpicon.android.codelab.pomodoro.core.SnackBarError
import pe.devpicon.android.codelab.pomodoro.domain.common.Error
import pe.devpicon.android.codelab.pomodoro.domain.common.ResultWrapper
import pe.devpicon.android.codelab.pomodoro.domain.model.Task
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.GetTaskByIdUseCase
import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode
import pe.devpicon.android.codelab.pomodoro.timer.annotation.TimerStatus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TimerViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private var status: @TimerStatus Int = TimerStatus.PAUSE
    private var detail: TimerDetail? = null
    private var counter: Long = POMODORO_DEFAULT_TIME
    private var mode: @PomodoroMode Int = PomodoroMode.POMODORO

    private var intervalJob: Job? = null

    private val _screenState: MutableLiveData<Event<TimerScreenState>> =
        MutableLiveData(Event(TimerScreenState.Initial))
    val screenState: LiveData<Event<TimerScreenState>>
        get() = _screenState

    private val _error: MutableLiveData<Event<SnackBarError>> = MutableLiveData()
    val error: LiveData<Event<SnackBarError>>
        get() = _error

    private val _onBakPressedDialog: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onBakPressedDialog: LiveData<Event<Boolean>>
        get() = _onBakPressedDialog

    fun postEvent(event: TimerScreenEvent) {
        when (event) {
            is TimerScreenEvent.LoadData -> {
                loadData(event.id)
            }
            is TimerScreenEvent.OnPlayStopButtonClicked -> {
                when (status) {
                    TimerStatus.PAUSE -> {
                        playCounter()
                    }
                    TimerStatus.PLAY -> {
                        stopCounter()
                    }
                }
            }
            is TimerScreenEvent.OnBackPressed -> {
                manageBack()
            }
        }
    }

    private fun manageBack() {
        _onBakPressedDialog.value = Event(
            when (status) {
                TimerStatus.PLAY -> true
                else -> false
            }
        )
    }

    private fun stopCounter() {
        status = TimerStatus.PAUSE
        _screenState.value = getDataLoadedEvent()
        intervalJob?.cancel()
    }

    private fun getDataLoadedEvent(): Event<TimerScreenState.DataLoaded> {
        return Event(
            TimerScreenState.DataLoaded(detail!!, counter, calculateProgress(), status, mode)
        )
    }

    private fun playCounter() {
        intervalJob = getTimer(counter, 1000)
            .map { time ->
                detail?.let { detail ->
                    counter = time
                    Event(
                        TimerScreenState.DataLoaded(detail, time, calculateProgress(), status, mode)
                    )
                }
            }.onStart {
                status = TimerStatus.PLAY
                getDataLoadedEvent()
            }
            .onEach {
                _screenState.value = it
            }
            .onCompletion {
                onPomodoroEnded()
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun onPomodoroEnded() {
        var currentPomodoro = detail!!.donePomodoros
        val totalPomodoros = detail?.total
        val isPomodoroPaused = status == TimerStatus.PAUSE

        if (isPomodoroPaused.not()) {
            when (mode) {
                PomodoroMode.POMODORO -> {
                    val isPomodoroEnded = (currentPomodoro + 1) == totalPomodoros
                    val isLongBreak = currentPomodoro != 0 && (currentPomodoro + 1) % 4 == 0

                    when {
                        isPomodoroEnded -> {
                            stopCounter()
                            counter = POMODORO_DEFAULT_TIME
                        }
                        isLongBreak -> {
                            mode = PomodoroMode.LONG_BREAK
                            counter = POMODORO_LONG_BREAK_DEFAULT_TIME
                        }
                        else -> {
                            mode = PomodoroMode.SHORT_BREAK
                            counter = POMODORO_SMALL_BREAK_DEFAULT_TIME
                        }
                    }

                    currentPomodoro += 1
                    detail = detail?.let {
                        TimerDetail(it.name, currentPomodoro, it.total)
                    }
                }
                PomodoroMode.SHORT_BREAK,
                PomodoroMode.LONG_BREAK -> {
                    counter = POMODORO_DEFAULT_TIME
                    mode = PomodoroMode.POMODORO
                }
            }
            stopCounter()
        }
    }

    private fun loadData(id: Long) {
        viewModelScope.launch {
            _screenState.value = Event(TimerScreenState.Loading)
            val result: ResultWrapper<Task> =
                getTaskByIdUseCase.invoke(GetTaskByIdUseCase.GetTaskByIdParams(id))
            when (result) {
                is ResultWrapper.Success -> {
                    onSuccess(result)

                }
                is ResultWrapper.Failure -> {
                    onFailure(result)
                }
            }
        }
    }

    private fun onSuccess(result: ResultWrapper.Success<Task>) {
        detail = getTaskDetail(result.value).also {
            _screenState.value = Event(
                TimerScreenState.DataLoaded(it, counter, calculateProgress(), status, mode)
            )
        }
    }

    private fun onFailure(result: ResultWrapper.Failure) {
        when (val error = result.error) {
            is Error.NetworkError -> showGenericError("Check internet connexion")
            is Error.GenericError -> {
                showGenericError(error.message)
            }
            is Error.UserEmailError -> showGenericError(error.message)
            is Error.UserPasswordError -> showGenericError(error.message)
        }
    }

    private fun showGenericError(error: String) {
        _error.value =
            Event(SnackBarError(error))
    }

    private fun calculateProgress(): Float {
        val maxTime = when (mode) {
            PomodoroMode.POMODORO -> POMODORO_DEFAULT_TIME
            PomodoroMode.LONG_BREAK -> POMODORO_LONG_BREAK_DEFAULT_TIME
            PomodoroMode.SHORT_BREAK -> POMODORO_SMALL_BREAK_DEFAULT_TIME
            else -> 0
        }

        return (counter.times(100) / maxTime).toFloat()
    }

    private fun getTaskDetail(task: Task): TimerDetail {
        return TimerDetail(task.name, task.donePomodoros, task.estimatedPomodoros)
    }
}
