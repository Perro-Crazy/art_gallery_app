package com.mauri.movieapp.presentation.feature.artlist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.domain.ArtListUseCase
import com.mauri.movieapp.foundation.AbstractViewModel
import com.mauri.movieapp.presentation.model.ArtVM
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class ArtListViewModel(
    handle: SavedStateHandle,
    private val artListUseCase: ArtListUseCase
) : AbstractViewModel<ArtListViewModel.State>(handle, FLOW_KEY, State.Loading) {

    init {
        viewModelScope.launch {
            handleInit()
        }
    }
    fun send(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.SelectArt -> handleSelectArt(event)
            }
        }
    }

    private suspend fun handleSelectArt(event: Event.SelectArt) {
        if(mutableState.value is State.Success) {
            setState((mutableState.value as State.Success).copy(selectedArt = event.art))
        }
    }

    private suspend fun handleInit() {
        if(mutableState.value is State.Loading) {
            setState(
                State.Success(
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
            )
        }
    }

    sealed class Event {
        data class SelectArt(val art: ArtVM?): Event()
    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()

        @Parcelize
        data class Success(
            val data: List<ArtVM>,
            val selectedArt: ArtVM? = null
        ) : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
