package com.mauri.movieapp.presentation.feature.artlist

import android.os.Bundle
import android.os.Debug
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtListActivity : ComponentActivity() {

    private val viewModel by viewModel<ArtListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.waitForDebugger()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "wait"
                        ) {

                            composable(
                                route = "detail/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                ArtListScreen.DetailRender(
                                    id = checkNotNull(it.arguments?.getString("id")),
                                    viewModel.state.value as ArtListViewModel.State.Success
                                )
                            }

                            composable("list") {
                                ArtListScreen.SuccessRender(
                                    viewModel.state.value as ArtListViewModel.State.Success,
                                    navController
                                )
                            }

                            composable("wait") {
                                ArtListScreen.LoadingRender(
                                    viewModel,
                                    lifecycleScope,
                                    navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
