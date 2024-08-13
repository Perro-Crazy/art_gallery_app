package com.mauri.movieapp.presentation.feature.movielist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.presentation.common.getMutableStateFlow
import com.mauri.movieapp.domain.MovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class MovieListViewModel(
    handle: SavedStateHandle,
    private val movieListUseCase: MovieListUseCase
) : ViewModel() {

    private val mutableState: MutableStateFlow<State> = handle.getMutableStateFlow(FLOW_KEY, State.Loading)
    val state: StateFlow<State>
        get() = mutableState

    init {
        handleLoading()
    }

    fun send(event: Event) {
        viewModelScope.launch {

        }
    }



    private fun handleLoading() {
        if(state.value is State.Loading) {
            movieListUseCase()
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
