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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import com.mauri.movieapp.presentation.feature.detail.DetailScreen
import com.mauri.movieapp.presentation.feature.list.ListScreen
import com.mauri.movieapp.presentation.feature.list.ListViewModel
import com.mauri.movieapp.presentation.feature.boot.LoadingScreen
import com.mauri.movieapp.presentation.model.ArtVM
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

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

                        composable<ArtVM> {
                            DetailScreen.Render(
                                art = it.toRoute(),
                                navController = navController
                            )
                        }

                        composable("list") {
                            ListScreen.ListRender(
                                viewModel = getViewModel(),
                                navController = navController
                            )
                        }

                        composable("error") {
                            Text(text = "Error")
                            Button(
                                onClick = {
                                    getViewModel<ListViewModel>().send(ListViewModel.Event.RetryInit)
                                    navController.navigate("wait") {
                                        popUpTo(0)
                                    }
                                }
                            ) {
                                Text(text = "Tentar novamente")
                            }
                        }

                        composable("wait") {
                            LoadingScreen.Render(
                                viewModel = getViewModel(),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
