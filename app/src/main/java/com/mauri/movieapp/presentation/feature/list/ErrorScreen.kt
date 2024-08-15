package com.mauri.movieapp.presentation.feature.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


object ErrorScreen {
    const val ROUTE = "error"
    @Composable
    fun Render(
        navController: NavHostController,
        viewModel: ListViewModel
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error")
                Button(
                    onClick = {
                        viewModel.send(ListViewModel.Event.RetryInit)
                        navController.navigate(LoadingScreen.ROUTE) {
                            popUpTo(0)
                        }
                    }
                ) {
                    Text(text = "Tentar novamente")
                }
            }
        }
    }
}
