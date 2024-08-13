package com.mauri.movieapp.presentation.feature.movielist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

object MovieListScreen {
    @Composable
    fun Render(state: MovieListViewModel.State) {
        when (state) {
            MovieListViewModel.State.Loading -> LoadingRender(state)
        }
    }

    @Composable
    private fun LoadingRender(state: MovieListViewModel.State) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
