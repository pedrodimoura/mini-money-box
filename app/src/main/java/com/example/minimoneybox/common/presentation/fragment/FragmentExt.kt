package com.example.minimoneybox.common.presentation.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.minimoneybox.R
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.common.presentation.viewmodel.UIAction
import com.example.minimoneybox.common.presentation.viewmodel.UIState
import com.example.minimoneybox.features.login.presentation.activity.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun <State : UIState, Action : UIAction> Fragment.onStateChanged(
    stateViewModel: StateViewModel<State, Action>,
    newState: (State) -> Unit
) = stateViewModel.states.observe(viewLifecycleOwner) { currentState -> newState(currentState) }

fun <State : UIState, Action : UIAction> Fragment.onAction(
    stateViewModel: StateViewModel<State, Action>,
    newAction: (Action) -> Unit
) = stateViewModel.actions.observe(viewLifecycleOwner) { currentAction -> newAction(currentAction) }

fun Fragment.showSessionExpiredDialog() {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.session_expired_title)
        .setMessage(R.string.session_expired_message)
        .setPositiveButton(R.string.session_expired_action_text) { _, _ ->
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        .setCancelable(false)
        .create()
        .show()
}