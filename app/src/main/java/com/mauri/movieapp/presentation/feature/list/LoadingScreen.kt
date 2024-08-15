package com.mauri.movieapp.presentation.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

object LoadingScreen {
    @Composable
    fun Render(
        navController: NavHostController,
        viewModel: ListViewModel
    ) {

        val state = viewModel.state.collectAsStateWithLifecycle().value

        if (state is ListViewModel.State.Success) {
            LaunchedEffect(navController) {
                navController.navigate(ListScreen.route) {
                    popUpTo(0)
                }
            }
        }

        if (state is ListViewModel.State.ErrorOnInitialLoad) {
            LaunchedEffect(navController) {
                navController.navigate("error") {
                    popUpTo(0)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
