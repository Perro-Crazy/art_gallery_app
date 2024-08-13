package com.mauri.movieapp.presentation.feature.movielist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class MovieListViewModel(
    handle: SavedStateHandle
) : ViewModel() {

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(
        handle.getStateFlow(FLOW_KEY, State.Loading).value
    )
    val state: StateFlow<State>
        get() = mutableState
    fun send(event: Event) {
        viewModelScope.launch {

        }
    }

    sealed class Event {

    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
