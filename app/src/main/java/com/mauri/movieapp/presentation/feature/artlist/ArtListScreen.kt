package com.mauri.movieapp.presentation.feature.artlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

object ArtListScreen {
    @Composable
    fun Render(state: ArtListViewModel.State) {
        when (state) {
            is ArtListViewModel.State.Loading -> LoadingRender()
            is ArtListViewModel.State.Success -> SuccessRender()
        }
    }

    @Composable
    private fun SuccessRender() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "AAAAAA")
        }
    }

    @Composable
    private fun LoadingRender() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
