package com.mauri.movieapp.presentation.feature.artlist

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.domain.ArtListUseCase
import com.mauri.movieapp.presentation.common.AbstractViewModel
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
                is Event.NextPage -> handleNextPage()
                is Event.SelectArt -> handleSelectArt(event)
            }
        }
    }

    private suspend fun handleSelectArt(event: Event.SelectArt) {
        if(state.value is State.Success) {
            setState(
                (state.value as State.Success).copy(selectedArt = event.art)
            )
        }
    }

    private suspend fun handleNextPage() {
        if(state.value is State.Success) {

            with((state.value as State.Success)) {
                setState(
                    artListUseCase(
                        ArtListUseCase.Parameter(
                            totalPages = totalPages,
                            currentPage = currentPage
                        )
                    ).let { artList ->
                        copy(
                            currentPage = artList.currentPage,
                            totalPages = artList.totalPages,
                            data = data + artList.data.map {
                                with(it) {
                                    ArtVM(
                                        id = id,
                                        title = title,
                                        mainReferenceNumber = mainReferenceNumber,
                                        artistDisplay = artistDisplay,
                                        description = description,
                                        origin = origin,
                                        image = image
                                    )
                                }
                            }
                        )
                    }
                )
            }
        }
    }

    private suspend fun handleInit() {
        if(state.value is State.Loading) {
            setState(
                with(artListUseCase()) {
                    State.Success(
                        totalPages = totalPages,
                        currentPage = currentPage,
                        data = data.map {
                            with(it) {
                                ArtVM(
                                    id = id,
                                    title = title,
                                    image = image,
                                    mainReferenceNumber = mainReferenceNumber,
                                    artistDisplay = artistDisplay,
                                    description = description,
                                    origin = origin
                                )
                            }
                        }
                    )
                }
            )
        }
    }

    sealed class Event {
        data object NextPage : Event()
        data class SelectArt(val art: ArtVM? = null) : Event()
    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()

        @Parcelize
        data class Success(
            val data: List<ArtVM>,
            val currentPage: Int,
            val totalPages: Int,
            val selectedArt: ArtVM? = null
        ) : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
