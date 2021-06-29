package com.example.minimoneybox.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class StateViewModel<State : UIState, Action : UIAction>(
    initialState: State
) : ViewModel() {

    private val _states = MutableLiveData<State>()
    private val _actions = MutableLiveData<Action>()

    init {
        _states.value = initialState
    }

    val states: LiveData<State> = _states
    val actions: LiveData<Action> = _actions

    fun setState(state: (State) -> State) {
        val currentState = _states.value!!
        val newState = state.invoke(currentState)
        _states.value = newState
    }

    fun sendAction(newAction: () -> Action) {
        _actions.value = newAction.invoke()
    }
}
