package pe.devpicon.android.codelab.pomodoro.task.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pe.devpicon.android.codelab.pomodoro.core.observeEvent
import pe.devpicon.android.codelab.pomodoro.core.showSnackBarError
import pe.devpicon.android.codelab.pomodoro.task.TaskNavigator
import pe.devpicon.android.codelab.pomodoro.task.databinding.FragmentTaskListBinding
import pe.devpicon.android.codelab.pomodoro.task.list.adapter.OnItemClickImpl
import pe.devpicon.android.codelab.pomodoro.task.list.adapter.TaskListAdapter
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var binding: FragmentTaskListBinding
    private val taskListAdapter: TaskListAdapter by lazy {
        TaskListAdapter(
            OnItemClickImpl(navigator, ::startActionMode)
        )
    }

    private var actionMode: ActionMode? = null

    @Inject
    lateinit var navigator: TaskNavigator

    private fun startActionMode() {
        this.actionMode = requireActivity().startActionMode(
            TaskListActionModeCallback()
        )
    }

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
                hideList()
            }
            is TaskListScreenState.DataLoaded -> {
                hideLoading()
                showAddButton()
                hideEmptyState()
                showAddButton()
                showList()

                this.taskListAdapter.submitList(state.taskList.toMutableList())
            }
            TaskListScreenState.EmptyState -> {
                hideLoading()
                hideAddButton()
                showEmptyState()
                hideList()

                this.taskListAdapter.submitList(mutableListOf())
            }
        }
    }

    private fun observeEffects() {
        viewModel.navigationToCreateTask.observeEvent(viewLifecycleOwner) {
            if (it) {
                navigator.navigateOnToCreateTask()
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
        configureStatusView()
        configureTaskList()
    }

    private fun setupAddButton() {
        binding.fbAddTask.setOnClickListener {
            viewModel.postEvent(TaskListScreenEvent.AddTaskPressed)
        }
    }

    private fun configureStatusView() {
        binding.statusView.setButtonAction {
            viewModel.postEvent(TaskListScreenEvent.AddTaskPressed)
        }
    }

    private fun configureTaskList() {
        with(binding.rvTasks) {
            this.adapter = taskListAdapter
            this.layoutManager = LinearLayoutManager(context)
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

    private fun showList() {
        binding.rvTasks.isVisible = true
    }

    private fun hideList() {
        binding.rvTasks.isGone = true
    }

}
