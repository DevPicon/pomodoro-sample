package pe.devpicon.android.codelab.pomodoro.task.list

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pe.devpicon.android.codelab.pomodoro.core.observeEvent
import pe.devpicon.android.codelab.pomodoro.core.showSnackBarError
import pe.devpicon.android.codelab.pomodoro.task.databinding.FragmentTaskListBinding

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var binding: FragmentTaskListBinding

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
        binding = FragmentTaskListBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModelChanges()
    }

    private fun observeViewModelChanges() {
        observeEffects()
        observeStates()
    }

    private fun observeStates() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            onNewState(it.peekContent())
        }
    }

    private fun onNewState(state: TaskListScreenState) {
        when (state) {
            TaskListScreenState.Initial -> viewModel.postEvent(TaskListScreenEvent.Load)
            TaskListScreenState.Loading -> {
                showLoading()
                hideAddButton()
                hideEmptyState()
            }
            is TaskListScreenState.DataLoaded -> {
                hideLoading()
                showAddButton()
                hideEmptyState()
                showAddButton()
            }
            TaskListScreenState.EmptyState -> {
                hideLoading()
                hideAddButton()
                showEmptyState()
            }
        }
    }

    private fun observeEffects() {
        viewModel.navigationToCreateTask.observeEvent(viewLifecycleOwner) {
            if (it) {
                TODO("Navigate to CreateTask")
            }
        }

        viewModel.error.observeEvent(viewLifecycleOwner) {
            if (it.show) {
                hideLoading()
                hideAddButton()
                showSnackBarError(it.message, Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun initViews() {
        setupAddButton()
    }

    private fun setupAddButton() {
        binding.fbAddTask.setOnClickListener {
            viewModel.postEvent(TaskListScreenEvent.AddTaskPressed)
        }
    }

    private fun hideAddButton() {
        binding.fbAddTask.isGone = true
    }

    private fun showAddButton() {
        binding.fbAddTask.isVisible = true
    }

    private fun showLoading() {
        binding.loader.isVisible = true
    }

    private fun hideLoading() {
        binding.loader.isGone = true
    }

    private fun showEmptyState() {
        binding.statusView.isVisible = true
    }

    private fun hideEmptyState() {
        binding.statusView.isGone = true
    }

}
