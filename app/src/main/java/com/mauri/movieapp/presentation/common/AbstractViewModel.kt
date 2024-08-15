package com.mauri.movieapp.presentation.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class AbstractViewModel<S,E>(
    private val handle: SavedStateHandle,
    private val keyState: String,
    defaultState: S
) : ViewModel() {

    private val mutableState: MutableStateFlow<S> = handle.getMutableStateFlow(keyState, defaultState)
    private val channel = Channel<E>(Channel.UNLIMITED)
    val state: StateFlow<S>
        get() = mutableState.asStateFlow()

    val effect
        get() = channel.receiveAsFlow()

    protected suspend fun <S> AbstractViewModel<S, E>.setState(state: S) {
        this.mutableState.emit(state)
        this.handle[keyState] = state
    }

    protected suspend fun <E> AbstractViewModel<S, E>.sendEffect(effect: E) {
        this.channel.send(effect)
    }
}
