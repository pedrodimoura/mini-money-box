package com.example.minimoneybox.features.login.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.common.presentation.fragment.onAction
import com.example.minimoneybox.common.presentation.fragment.onStateChanged
import com.example.minimoneybox.databinding.FragmentLoginBinding
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginAction
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginViewModel
import com.example.minimoneybox.ui.ErrorBottomSheetArgs
import com.example.minimoneybox.ui.errorBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewBinding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeStates()
        observeActions()
    }

    private fun setupViews() {
        viewBinding.btnSignIn.setOnClickListener {
            viewModel.authenticate(
                LoginParams(
                    viewBinding.etEmail.text.toString(),
                    viewBinding.etPassword.text.toString(),
                    viewBinding.etName.text.toString()
                )
            )
        }
    }

    private fun observeStates() {
        onStateChanged(viewModel) { state ->
            showLoading(state.isLoading)
            showEmailErrorMessage(state.isEmailInvalid)
            showPasswordErrorMessage(state.isPasswordEmpty)
        }
    }

    private fun observeActions() {
        onAction(viewModel) { action ->
            when (action) {
                is LoginAction.OpenAccountsScreen -> navigateToAccountsActivity()
                is LoginAction.OpenErrorScreen -> navigateToErrorFragment(action.message)
            }
        }
    }

    private fun navigateToAccountsActivity() {
        val name: String = viewBinding.etName.text.toString()
        findNavController().navigate(
            LoginFragmentDirections.fromFragmentLoginToAccountsActivity(name)
        )
        requireActivity().finish()
    }

    private fun navigateToErrorFragment(message: String) {
        errorBottomSheet(ErrorBottomSheetArgs(message)).show(
            childFragmentManager,
            this::class.java.simpleName
        )
    }

    private fun showLoading(show: Boolean) {
        viewBinding.btnSignIn.isEnabled = show.not()
        viewBinding.etEmail.isEnabled = show.not()
        viewBinding.etPassword.isEnabled = show.not()
        viewBinding.etName.isEnabled = show.not()
        when {
            show -> viewBinding.animation.playAnimation()
            else -> viewBinding.animation.cancelAnimation()
        }
    }

    private fun showEmailErrorMessage(show: Boolean) {
        if (show)
            viewBinding.etEmail.error = getString(R.string.email_address_error)
        else viewBinding.etEmail.error = null
    }

    private fun showPasswordErrorMessage(show: Boolean) {
        if (show)
            viewBinding.etPassword.error = getString(R.string.error_incorrect_password)
        else viewBinding.etPassword.error = null
    }
}
