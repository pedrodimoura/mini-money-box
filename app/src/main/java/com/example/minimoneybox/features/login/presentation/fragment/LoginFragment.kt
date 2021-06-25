package com.example.minimoneybox.features.login.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.databinding.FragmentLoginBinding
import com.example.minimoneybox.features.login.domain.model.LoginParams
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginAction
import com.example.minimoneybox.features.login.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "LoginFragment"

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
            viewBinding.animation.playAnimation()
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
        viewModel.states.observe(viewLifecycleOwner) {
            showLoading(it.isLoading)
        }
    }

    private fun observeActions() {
        viewModel.actions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is LoginAction.OpenAccountsScreen -> navigateToAccountsActivity()
                is LoginAction.OpenErrorScreen -> navigateToErrorFragment()
            }
        }
    }

    private fun navigateToAccountsActivity() {
        findNavController().navigate(LoginFragmentDirections.fromFragmentLoginToAccountsActivity())
    }

    private fun navigateToErrorFragment() {
        findNavController().navigate(LoginFragmentDirections.fromFragmentLoginToErrorFragment())
    }

    private fun showLoading(show: Boolean) {
        Log.i(TAG, "showLoading: $show") // TODO: Remove this later
    }
}
