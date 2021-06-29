package com.example.minimoneybox.features.account.presentation.fragment

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.binding.viewBinding
import com.example.minimoneybox.common.presentation.fragment.onAction
import com.example.minimoneybox.common.presentation.fragment.onStateChanged
import com.example.minimoneybox.common.presentation.fragment.showSessionExpiredDialog
import com.example.minimoneybox.databinding.FragmentProductDetailsBinding
import com.example.minimoneybox.features.account.presentation.viewmodel.ProductDetailsViewModel
import com.example.minimoneybox.features.account.presentation.viewmodel.action.ProductDetailsAction
import com.example.minimoneybox.ui.ErrorBottomSheetArgs
import com.example.minimoneybox.ui.errorBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val viewBinding: FragmentProductDetailsBinding by viewBinding()
    private val args: ProductDetailsFragmentArgs by navArgs()

    private val viewModel: ProductDetailsViewModel by viewModels()

    private val numberFormat by lazy { NumberFormat.getCurrencyInstance(Locale.UK) }

    private val quickAdd: ((Int) -> Unit) = { amountToAdd ->
        viewModel.quickAdd(amountToAdd.toDouble(), args.product.id)
    }

    private val successAnimationListener = object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
            viewBinding.successLottieAnimation.isVisible = false
        }

        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarConfig()
        observeStates()
        observeActions()
        inflateProductDetailsOnUI()
        setQuickAddListeners()
        setSuccessAnimationListener()
    }

    override fun onStop() {
        super.onStop()
        remoteSuccessAnimationListener()
    }

    private fun setToolbarConfig() {
        viewBinding.productDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeStates() {
        onStateChanged(viewModel) { state ->
            showLoading(state.isLoading)
        }
    }

    private fun observeActions() {
        onAction(viewModel) { action ->
            when (action) {
                is ProductDetailsAction.UpdateMoneyboxAmount -> updateMoneyboxAmount(action.amount)
                is ProductDetailsAction.OpenErrorScreen -> openErrorScreen(action.message)
                is ProductDetailsAction.LogoutUser -> showSessionExpiredDialog()
            }
        }
    }

    private fun inflateProductDetailsOnUI() {
        viewBinding.apply {
            productNameTextView.text = args.product.friendlyName
            productPlanValueTextView.text = numberFormat.format(args.product.planValue)
            moneyboxAmount.text = numberFormat.format(args.product.moneybox)
        }
    }

    private fun setQuickAddListeners() {
        viewBinding.apply {
            quickAdd10.quickAddClick(quickAdd)
            quickAdd20.quickAddClick(quickAdd)
            quickAdd30.quickAddClick(quickAdd)
        }
    }

    private fun setSuccessAnimationListener() =
        viewBinding.successLottieAnimation.addAnimatorListener(successAnimationListener)

    private fun remoteSuccessAnimationListener() =
        viewBinding.successLottieAnimation.removeAllAnimatorListeners()

    private fun showLoading(show: Boolean) {
        viewBinding.quickAddScrollView.isVisible = show.not()
        viewBinding.loadingLottieAnimation.apply {
            isVisible = show
            if (show)
                playAnimation()
            else cancelAnimation()
        }
    }

    private fun updateMoneyboxAmount(amount: Double) {
        viewBinding.moneyboxAmount.text = numberFormat.format(amount)
        isQuickAddEnabled(amount)
        showSuccessAnimation()
    }

    private fun isQuickAddEnabled(amount: Double) {
        viewBinding.quickAddScrollView.isEnabled = (args.product.planValue >= amount)
    }

    private fun showSuccessAnimation() {
        viewBinding.successLottieAnimation.isVisible = true
        viewBinding.successLottieAnimation.playAnimation()
    }

    private fun openErrorScreen(message: String) {
        errorBottomSheet(ErrorBottomSheetArgs(message))
            .show(childFragmentManager, this::class.java.simpleName)
    }
}
