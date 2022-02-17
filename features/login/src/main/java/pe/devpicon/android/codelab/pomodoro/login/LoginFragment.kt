package pe.devpicon.android.codelab.pomodoro.login

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.devpicon.android.codelab.pomodoro.core.observeEvent
import pe.devpicon.android.codelab.pomodoro.core.showSnackBarError
import pe.devpicon.android.codelab.pomodoro.login.databinding.FragmentLoginBinding
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var navigator: LoginNavigator

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper: Context =
            ContextThemeWrapper(
                requireContext(), pe.devpicon.android.codelab.pomodoro.core.R.style.LoginTheme
            )
        val localInflater: LayoutInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = FragmentLoginBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        initViews()
    }

    private fun initViews() {
        binding.txtEmail.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.postEvent(
                LoginScreenEvent.OnTextChanged(
                    text.toString(),
                    binding.txtPassword.text.toString(),
                    binding.txtConfirmPassword.text.toString()
                )
            )
        })

        binding.txtPassword.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.postEvent(
                LoginScreenEvent.OnTextChanged(
                    binding.txtEmail.text.toString(),
                    text.toString(),
                    binding.txtConfirmPassword.text.toString()
                )
            )
        })

        binding.txtConfirmPassword.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.postEvent(
                LoginScreenEvent.OnTextChanged(
                    binding.txtEmail.text.toString(),
                    binding.txtPassword.text.toString(),
                    text.toString()
                )
            )
        })

        binding.btnLogin.setOnClickListener {
            viewModel.postEvent(LoginScreenEvent.OnMainButtonClicked)
        }

        binding.btnSecondary.setOnClickListener {
            viewModel.postEvent(LoginScreenEvent.OnSecondaryButtonClicked)
        }
    }

    private fun observeViewModelChanges() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            onNewState(it.peekContent())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showSnackBarError(it.peekContent().message)
        }

        viewModel.navigationToTaskList.observeEvent(viewLifecycleOwner) {
            if (it) {
                navigator.navigateOnLoginSuccess()
            }
        }
    }

    private fun onNewState(state: LoginScreenState) {
        when (state) {
            is LoginScreenState.Loading -> showLoading()
            is LoginScreenState.SignIn -> {
                showContent()
                binding.tilConfirmPassword.isGone = true
                binding.btnLogin.text = resources.getString(R.string.login_sign_in)
                binding.btnSecondary.text =
                    resources.getString(R.string.login_create_an_account)
            }
            is LoginScreenState.SignUp -> {
                showContent()
                binding.tilConfirmPassword.isVisible = true
                binding.btnSecondary.text = resources.getString(R.string.login_sign_in_instead)
                binding.btnLogin.text = resources.getString(R.string.login_sign_up)
            }
            is LoginScreenState.Success -> Toast.makeText(
                requireActivity(),
                state.data,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading() {
        with(binding) {
            loginContainer.isGone = true
            loginLoader.isVisible = true
        }
    }

    private fun showContent() {
        with(binding) {
            loginContainer.isVisible = true
            loginLoader.isGone = true
        }
    }
}
