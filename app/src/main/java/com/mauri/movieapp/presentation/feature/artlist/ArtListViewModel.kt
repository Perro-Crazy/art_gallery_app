package com.mauri.movieapp.presentation.feature.artlist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.presentation.common.getMutableStateFlow
import com.mauri.movieapp.domain.ArtListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class ArtListViewModel(
    handle: SavedStateHandle,
    private val artListUseCase: ArtListUseCase
) : ViewModel() {

    private val mutableState: MutableStateFlow<State> = handle.getMutableStateFlow(FLOW_KEY, State.Loading)
    val state: StateFlow<State>
        get() = mutableState

    fun send(event: Event) {
        viewModelScope.launch {
            when (event) {
                Event.Init -> handleInit()
            }
        }
    }

    private suspend fun handleInit() {
        if(state.value is State.Loading) {
            artListUseCase()
        }
    }

    sealed class Event {
        data object Init: Event()
    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
