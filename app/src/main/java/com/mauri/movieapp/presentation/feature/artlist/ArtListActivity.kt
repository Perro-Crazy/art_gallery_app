package com.mauri.movieapp.presentation.feature.artlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtListActivity : ComponentActivity() {

    private val viewModel by viewModel<ArtListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = "wait"
                    ) {

                        composable("detail") {
                            ArtListScreen.DetailRender(
                                state = (viewModel.state.collectAsState().value as ArtListViewModel.State.Success),
                                onBack = {
                                    viewModel.send(ArtListViewModel.Event.SelectArt())
                                    navController.popBackStack(
                                        route = "list",
                                        inclusive = false,
                                        saveState = false
                                    )
                                }
                            )
                        }

                        composable("list") {
                            ArtListScreen.ListRender(
                                state = (viewModel.state.collectAsState().value as ArtListViewModel.State.Success),
                                onNextPage = {
                                    viewModel.send(ArtListViewModel.Event.NextPage)
                                },
                                onSelectItem = {
                                    viewModel.send(ArtListViewModel.Event.SelectArt(it))
                                    navController.navigate("detail")
                                }
                            )
                        }

                        composable("wait") {
                            ArtListScreen.LoadingRender(
                                state = viewModel.state.collectAsState().value,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
