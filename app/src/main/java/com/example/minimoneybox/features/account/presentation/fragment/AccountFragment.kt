package com.example.minimoneybox.features.account.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.common.presentation.fragment.onAction
import com.example.minimoneybox.common.presentation.fragment.onStateChanged
import com.example.minimoneybox.databinding.FragmentAccountBinding
import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.presentation.adapter.AccountRecyclerViewAdapter
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountAction
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AccountFragment"

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val viewBinding: FragmentAccountBinding by viewBinding()
    private val viewModel: AccountViewModel by viewModels()
    private val args: AccountFragmentArgs by navArgs()

    private val accountRecyclerViewAdapter by lazy { AccountRecyclerViewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        observeActions()
        inflateArgumentsOnView()
        setupAccountRecyclerViewAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchAccountInformation()
    }

    private fun observeStates() {
        onStateChanged(viewModel) { state ->
            showLoading(state.isLoading)
            showContent(state.isContentVisible)
        }
    }

    private fun observeActions() {
        onAction(viewModel) { action ->
            when (action) {
                is AccountAction.ShowAccountInformationOnUI ->
                    showAccountInformationOnUI(action.accountInformation)
                is AccountAction.OpenErrorScreen -> Log.i(TAG, "Error: ${action.message}")
                is AccountAction.LogoutUser -> {
                    requireActivity().finish()
                    // TODO: Show Logout Dialog!!
                }
            }
        }
    }

    private fun inflateArgumentsOnView() {
        val greetingsMessage = when {
            args.name.isNullOrEmpty() -> getString(R.string.account_activity_default_greeting_text)
            else -> getString(R.string.account_activity_greeting_text, args.name)
        }

        viewBinding.accountsTextView.text = HtmlCompat.fromHtml(
            greetingsMessage,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun setupAccountRecyclerViewAdapter() {
        viewBinding.recyclerViewAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountRecyclerViewAdapter
        }
    }

    private fun showLoading(show: Boolean) {
        Log.i(TAG, "showLoading: $show")
    }

    private fun showContent(show: Boolean) {
        viewBinding.recyclerViewAccounts.isVisible = show
    }

    private fun showAccountInformationOnUI(accountInformation: AccountInformation) {
        Log.i(TAG, "showAccountInformationOnUI: ${accountInformation.accounts.size}")
        accountRecyclerViewAdapter.submitList(accountInformation.accounts)
    }

}