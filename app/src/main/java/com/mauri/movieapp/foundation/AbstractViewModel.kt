package com.mauri.movieapp.foundation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mauri.movieapp.presentation.common.getMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AbstractViewModel<S>(
    private val handle: SavedStateHandle,
    private val keyState: String,
    defaultState: S
) : ViewModel() {

    protected val mutableState: MutableStateFlow<S> = handle.getMutableStateFlow(keyState, defaultState)
    val state: StateFlow<S>
        get() = mutableState.asStateFlow()

    protected suspend fun <T> AbstractViewModel<T>.setState(state: T) {
        this.mutableState.emit(state)
        this.handle[keyState] = state
    }
}
