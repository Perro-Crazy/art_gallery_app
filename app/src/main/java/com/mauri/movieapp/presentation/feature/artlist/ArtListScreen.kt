package com.mauri.movieapp.presentation.feature.artlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

object ArtListScreen {
    @Composable
    fun Render(state: ArtListViewModel.State) {
        when (state) {
            ArtListViewModel.State.Loading -> LoadingRender(state)
        }
    }

    @Composable
    private fun LoadingRender(state: ArtListViewModel.State) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
