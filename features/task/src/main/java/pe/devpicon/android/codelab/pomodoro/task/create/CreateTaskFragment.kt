package pe.devpicon.android.codelab.pomodoro.task.create

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pe.devpicon.android.codelab.pomodoro.core.observeEvent
import pe.devpicon.android.codelab.pomodoro.core.removeErrorOnTyping
import pe.devpicon.android.codelab.pomodoro.core.showSnackBar
import pe.devpicon.android.codelab.pomodoro.core.showSnackBarError
import pe.devpicon.android.codelab.pomodoro.task.R
import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator
import pe.devpicon.android.codelab.pomodoro.task.databinding.FragmentCreateTaskBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    private val viewModel: CreateTaskViewModel by viewModels()
    private lateinit var binding: FragmentCreateTaskBinding

    lateinit var navigator: TaskNavigator

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
        binding = FragmentCreateTaskBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModelChanges()
        observeEffects()

    }

    private fun observeEffects() {
        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBarError(it.peekContent().message)
        }

        viewModel.navigateToTaskList.observeEvent(viewLifecycleOwner) {
            if (it) {
                navigateToBack()
            }
        }
    }

    private fun observeViewModelChanges() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            onNewState(it.peekContent())
        }

    }

    private fun onNewState(state: CreateTaskScreenState) {
        when (state) {
            is CreateTaskScreenState.Start -> {
                hideLoading()
                showUI()
                binding.tilTaskName.editText?.setText(state.name)
                binding.pcvCounter.count = state.estimated
            }
            is CreateTaskScreenState.InvalidName -> {
                showUI()
                binding.tilTaskName.error = getString(R.string.create_task_invalid_name)
            }
            is CreateTaskScreenState.Loading -> {
                showLoading()
                hideUI()
            }
            is CreateTaskScreenState.Success -> {
                showSnackBar(
                    getString(R.string.create_task_success),
                    Snackbar.LENGTH_LONG,
                    pe.devpicon.android.codelab.pomodoro.core.R.color.success_color
                )
                hideLoading()
                showUI()
            }
        }
    }

    private fun initViews() {
        configureToolbar()
        configureEditText()
        configureCounter()
    }

    private fun configureEditText() {
        binding.tilTaskName.removeErrorOnTyping()
        binding.txTaskName.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.postEvent(
                CreateTaskScreenEvent.EditingTask(
                    text.toString(),
                    binding.pcvCounter.count
                )
            )
        })
    }

    private fun configureCounter() {
        binding.pcvCounter.count = 1
        binding.pcvCounter.onCounterClicked().onEach {
            viewModel.postEvent(
                CreateTaskScreenEvent.EditingTask(
                    binding.txTaskName.text.toString(),
                    it
                )
            )
        }.launchIn(lifecycleScope)
    }

    private fun configureToolbar() {
        with(binding.toolbar) {
            setNavigationIcon(pe.devpicon.android.codelab.pomodoro.core.R.drawable.ic_action_back)
            setNavigationOnClickListener {
                navigateToBack()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_save -> {
                        viewModel.postEvent(CreateTaskScreenEvent.SaveTask)
                    }
                }
                true
            }
        }
    }

    private fun navigateToBack() {
        navigator.onBack()
    }

    private fun showLoading() {
        binding.loader.isVisible = true
    }

    private fun hideLoading() {
        binding.loader.isGone = true
    }

    private fun showUI() {
        binding.container.isVisible = true
    }

    private fun hideUI() {
        binding.container.isGone = true
    }
}
