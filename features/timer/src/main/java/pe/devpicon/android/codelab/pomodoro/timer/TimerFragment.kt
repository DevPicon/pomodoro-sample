package pe.devpicon.android.codelab.pomodoro.timer

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pe.devpicon.android.codelab.pomodoro.core.observeEvent
import pe.devpicon.android.codelab.pomodoro.core.showSnackBarError
import pe.devpicon.android.codelab.pomodoro.timer.annotation.PomodoroMode
import pe.devpicon.android.codelab.pomodoro.timer.annotation.TimerStatus
import pe.devpicon.android.codelab.pomodoro.timer.databinding.FragmentTimerBinding
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class TimerFragment : Fragment() {

    private val viewModel: TimerViewModel by viewModels()

    @Inject
    lateinit var navigator: TimerNavigator
    private val args: TimerFragmentArgs by navArgs()
    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper: Context =
            ContextThemeWrapper(
                requireContext(),
                pe.devpicon.android.codelab.pomodoro.core.R.style.LoginTheme
            )

        val localInflater: LayoutInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = FragmentTimerBinding.inflate(localInflater)
        //binding = FragmentTimerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        observeEffects()
        initViews()
    }


    private fun observeViewModelChanges() {
        viewModel.onBakPressedDialog.observeEvent(viewLifecycleOwner) {
            if (it) {
                showBackDialog()
            } else {
                navigator.onBack()
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) {
            onNewState(it.peekContent())
        }
    }

    private fun observeEffects() {

        viewModel.error.observeEvent(viewLifecycleOwner) {
            if (it.show) {
                showSnackBarError(it.message, Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun onNewState(state: TimerScreenState) {
        when (state) {
            is TimerScreenState.Initial -> {
                viewModel.postEvent(TimerScreenEvent.LoadData(args.taskId))
            }

            is TimerScreenState.DataLoaded -> {
                setUptDetail(state.taskDetail, state.mode)
                setUpTimer(state.time, state.progress)
                setUpPlayButton(state.status)
            }
            TimerScreenState.Loading -> {
                // Pending
            }
        }
    }

    private fun setUptDetail(taskDetail: TimerDetail, @PomodoroMode mode: Int) {
        binding.txPomodoros.text = when (mode) {
            PomodoroMode.POMODORO -> {
                getString(
                    R.string.timer_done_pomodoros,
                    taskDetail.donePomodoros,
                    taskDetail.total
                )
            }
            PomodoroMode.SHORT_BREAK -> {
                "Time to have a break"
            }

            PomodoroMode.LONG_BREAK -> {
                "Time to have a Long break"
            }

            else -> getString(
                R.string.timer_done_pomodoros,
                taskDetail.donePomodoros,
                taskDetail.total
            )
        }
    }

    private fun setUpTimer(time: Long, progress: Float) {
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60
        binding.txTimer.text = getString(R.string.timer_timer, minutes, seconds)
        binding.timerProgress.progress = progress
    }

    private fun setUpPlayButton(@TimerStatus mode: Int) {
        binding.playButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(), when (mode) {
                    TimerStatus.PLAY -> {
                        R.drawable.ic_baseline_pause_24
                    }
                    TimerStatus.PAUSE -> {
                        R.drawable.ic_baseline_play_arrow
                    }
                    else -> R.drawable.ic_baseline_play_arrow
                }
            )
        )
        binding.playButton.setOnClickListener {
            viewModel.postEvent(TimerScreenEvent.OnPlayStopButtonClicked)
        }
    }

    private fun showBackDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setMessage("Do you want to leave this task?")
        builder.setPositiveButton("Yes") { _, _ ->
            navigator.onBack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.show()
    }

    private fun initViews() {
        args.taskName?.let {
            with(binding.toolbar) {
                title = it
                setNavigationIcon(pe.devpicon.android.codelab.pomodoro.core.R.drawable.ic_action_back)
                setNavigationOnClickListener {
                    viewModel.postEvent(TimerScreenEvent.OnBackPressed)
                }
            }
        }
    }
}
