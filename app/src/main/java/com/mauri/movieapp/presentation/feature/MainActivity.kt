package com.mauri.movieapp.presentation.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import com.mauri.movieapp.presentation.feature.list.ArtListScreen
import com.mauri.movieapp.presentation.feature.list.ArtListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

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

                        composable(
                            route = "detail/{id_art}",
                            arguments = listOf(
                                navArgument("id_art") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            ArtListScreen.DetailRender(
                                idArt = checkNotNull(it.arguments?.getInt("id_art")),
                                state = (viewModel.state.collectAsState().value as ArtListViewModel.State.Success),
                                onBack = {
                                    navController.popBackStack()
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
                                    navController.navigate("detail/${it}")
                                }
                            )
                        }

                        composable("error") {
                            Text(text = "Error")
                            Button(
                                onClick = {
                                    viewModel.send(ArtListViewModel.Event.RetryInit)
                                    navController.navigate("wait") {
                                        popUpTo(0)
                                    }
                                }
                            ) {
                                Text(text = "Tentar novamente")
                            }
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
