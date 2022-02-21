package pe.devpicon.android.codelab.pomodoro.timer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.devpicon.android.codelab.pomodoro.domain.usecase.task.GetTaskByIdUseCase
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}
