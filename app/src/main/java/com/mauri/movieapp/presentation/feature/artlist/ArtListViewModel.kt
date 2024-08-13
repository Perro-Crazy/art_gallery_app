package com.mauri.movieapp.presentation.feature.artlist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.presentation.common.getMutableStateFlow
import com.mauri.movieapp.domain.ArtListUseCase
import com.mauri.movieapp.presentation.model.ArtVM
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
            mutableState.value = State.Success(
                data = artListUseCase().map {
                    with(it) {
                        ArtVM(
                            id = id,
                            title = title,
                            mainReferenceNumber = mainReferenceNumber,
                            artistDisplay = artistDisplay,
                            image = image
                        )
                    }
                }
            )
        }
    }

    sealed class Event {
        data object Init: Event()
    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()

        @Parcelize
        data class Success(val data: List<ArtVM>) : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
