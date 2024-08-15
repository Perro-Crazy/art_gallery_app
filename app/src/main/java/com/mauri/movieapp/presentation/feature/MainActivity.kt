package com.mauri.movieapp.presentation.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import com.mauri.movieapp.presentation.feature.detail.DetailScreen
import com.mauri.movieapp.presentation.feature.list.ErrorScreen
import com.mauri.movieapp.presentation.feature.list.ListScreen
import com.mauri.movieapp.presentation.feature.list.LoadingScreen
import com.mauri.movieapp.presentation.model.ArtVM
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MovieAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = LoadingScreen.ROUTE
                ) {

                    composable<ArtVM> {
                        DetailScreen.Render(
                            art = it.toRoute(),
                            navController = navController
                        )
                    }

                    composable(ListScreen.ROUTE) {
                        ListScreen.ListRender(
                            viewModel = getViewModel(),
                            navController = navController
                        )
                    }

                    composable(ErrorScreen.ROUTE) {
                        ErrorScreen.Render(
                            viewModel = getViewModel(),
                            navController = navController
                        )
                    }

                    composable(LoadingScreen.ROUTE) {
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
