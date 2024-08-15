package com.mauri.movieapp.presentation.feature.list

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mauri.movieapp.domain.ListInitialDataUseCase
import com.mauri.movieapp.domain.ListPerPageUseCase
import com.mauri.movieapp.presentation.common.AbstractViewModel
import com.mauri.movieapp.presentation.model.ArtVM
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class ListViewModel(
    handle: SavedStateHandle,
    private val listInitialDataUseCase: ListInitialDataUseCase,
    private val listPerPageUseCase: ListPerPageUseCase
) : AbstractViewModel<ListViewModel.State, ListViewModel.Effect>(handle, FLOW_KEY, State.Loading) {

    init {
        viewModelScope.launch {
            if (state.value is State.Loading) handleInit()
        }
    }
    fun send(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.RetryInit -> handleInit()
                is Event.NextPage -> handleNextPage()
            }
        }
    }

    private suspend fun handleNextPage() {
        runCatching {
            with((state.value as State.Success)) {
                listPerPageUseCase(
                    ListPerPageUseCase.Parameter(
                        totalPages = totalPages,
                        currentPage = currentPage
                    )
                ).let { artList ->
                    copy(
                        currentPage = artList.currentPage,
                        totalPages = artList.totalPages,
                        data = data + artList.data.map { ArtVM.from(it) },
                        errorOnNextPage = false
                    )
                }.run {
                    setState(this)
                }
            }
        }.onFailure {
            sendEffect(Effect.Error)
//            with((state.value as State.Success)) {
//                setState(copy(errorOnNextPage = true))
//            }
        }
    }

    private suspend fun handleInit() {
        runCatching {
            setState(
                with(listInitialDataUseCase()) {
                    State.Success(
                        totalPages = totalPages,
                        currentPage = currentPage,
                        data = data.map { ArtVM.from(it) }
                    )
                }
            )
        }.onFailure {
            setState(State.ErrorOnInitialLoad)
        }
    }

    sealed class Event {
        data object RetryInit : Event()
        data object NextPage : Event()
    }

    sealed class Effect {
        data object Error: Effect()
    }

    sealed class State : Parcelable {
        @Parcelize
        data object Loading : State()

        @Parcelize
        data class Success(
            val data: List<ArtVM>,
            val currentPage: Int,
            val totalPages: Int,
            val errorOnNextPage: Boolean = false
        ) : State()

        @Parcelize
        data object ErrorOnInitialLoad : State()
    }

    companion object  {
        private const val FLOW_KEY = "MovieListFlowKey"
    }
}
