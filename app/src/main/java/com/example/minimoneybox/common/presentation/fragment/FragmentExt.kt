package com.example.minimoneybox.common.presentation.fragment

import androidx.fragment.app.Fragment
import com.example.minimoneybox.common.presentation.viewmodel.StateViewModel
import com.example.minimoneybox.common.presentation.viewmodel.UIAction
import com.example.minimoneybox.common.presentation.viewmodel.UIState

fun <State : UIState, Action : UIAction> Fragment.onStateChanged(
    stateViewModel: StateViewModel<State, Action>,
    newState: (State) -> Unit
) = stateViewModel.states.observe(viewLifecycleOwner) { currentState -> newState(currentState) }

fun <State : UIState, Action : UIAction> Fragment.onAction(
    stateViewModel: StateViewModel<State, Action>,
    newAction: (Action) -> Unit
) = stateViewModel.actions.observe(viewLifecycleOwner) { currentAction -> newAction(currentAction) }