package com.example.minimoneybox.features.account.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.common.presentation.fragment.onAction
import com.example.minimoneybox.common.presentation.fragment.onStateChanged
import com.example.minimoneybox.common.presentation.fragment.showSessionExpiredDialog
import com.example.minimoneybox.databinding.FragmentAccountBinding
import com.example.minimoneybox.features.account.domain.model.AccountInformation
import com.example.minimoneybox.features.account.domain.model.Product
import com.example.minimoneybox.features.account.presentation.adapter.AccountItemDecoration
import com.example.minimoneybox.features.account.presentation.adapter.AccountRecyclerViewAdapter
import com.example.minimoneybox.features.account.presentation.model.ProductView
import com.example.minimoneybox.features.account.presentation.viewmodel.AccountViewModel
import com.example.minimoneybox.features.account.presentation.viewmodel.action.AccountAction
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val viewBinding: FragmentAccountBinding by viewBinding()
    private val viewModel: AccountViewModel by viewModels()
    private val args: AccountFragmentArgs by navArgs()

    private val accountRecyclerViewAdapter by lazy {
        AccountRecyclerViewAdapter { product -> this.onProductSelected(product) }
    }

    private val numberFormat: NumberFormat by lazy { NumberFormat.getCurrencyInstance(Locale.UK) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        observeActions()
        inflateArgumentsOnView()
        setupAccountRecyclerViewAdapter()
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
                is AccountAction.ShowAccountInformationOnUI -> showAccountInformationOnUI(action.accountInformation)
                is AccountAction.OpenErrorScreen -> findNavController().navigate(
                    AccountFragmentDirections.fromFragmentAccountToFragmentError(action.message)
                )
                is AccountAction.LogoutUser -> showSessionExpiredDialog()
            }
        }
    }

    private fun inflateArgumentsOnView() {
        when {
            args.name.isNullOrEmpty() -> {
                viewBinding.greetingsBodyTextView.text =
                    getString(R.string.account_activity_greeting_no_name)
            }
            else -> {
                viewBinding.greetingsHeaderTextView.text =
                    getString(R.string.account_activity_greeting_header_with_name)
                viewBinding.greetingsBodyTextView.text =
                    getString(R.string.account_activity_greeting_body, args.name)
            }
        }
    }

    private fun setupAccountRecyclerViewAdapter() {
        viewBinding.recyclerViewAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountRecyclerViewAdapter
            addItemDecoration(AccountItemDecoration())
        }
    }

    private fun showLoading(show: Boolean) {
        viewBinding.loadingLottieAnimation.isVisible = show
        viewBinding.loadingMessageTextView.isVisible = show
        when {
            show -> viewBinding.loadingLottieAnimation.playAnimation()
            else -> viewBinding.loadingLottieAnimation.cancelAnimation()
        }
    }

    private fun showContent(show: Boolean) {
        viewBinding.totalTextView.isVisible = show
        viewBinding.recyclerViewAccounts.isVisible = show
    }

    private fun showAccountInformationOnUI(accountInformation: AccountInformation) {
        val totalPlanValue = numberFormat.format(accountInformation.totalPlanValue)
        viewBinding.totalTextView.text = getString(R.string.total_plan_value, totalPlanValue)
        accountRecyclerViewAdapter.submitList(accountInformation.products)
    }

    private fun onProductSelected(product: Product) {
        val productView = ProductView(
            product.id,
            product.planValue,
            product.moneybox,
            product.productDetail.name,
            product.productDetail.category,
            product.productDetail.friendlyName,
        )
        findNavController().navigate(
            AccountFragmentDirections.fromFragmentAccountToFragmentProductDetails(productView)
        )
    }

}