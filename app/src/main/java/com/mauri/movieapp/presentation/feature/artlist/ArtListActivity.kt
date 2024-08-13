package com.mauri.movieapp.presentation.feature.artlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mauri.movieapp.presentation.common.theme.MovieAppTheme
import com.mauri.movieapp.presentation.model.ArtVM
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtListActivity : ComponentActivity() {

    private val viewModel by viewModel<ArtListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) viewModel.send(ArtListViewModel.Event.Init)
        enableEdgeToEdge()
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                setContent {
                    val navController = rememberNavController()
                    MovieAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Box(modifier = Modifier.padding(innerPadding)) {
                                NavHost(
                                    navController =  navController,
                                    startDestination = "wait"
                                ) {

                                    composable("detail") {
                                        ArtListScreen.DetailRender(state as ArtListViewModel.State.Success)
                                    }

                                    composable("list") {
                                        ArtListScreen.SuccessRender(
                                            state = state as ArtListViewModel.State.Success,
                                            screenInteraction = object : ArtListScreen.ScreenInteraction {
                                                override fun onSelectedArt(art: ArtVM) {
                                                    viewModel.send(ArtListViewModel.Event.SelectArt(art))
                                                }
                                            }
                                        )
                                    }

                                    composable("wait") {
                                        ArtListScreen.LoadingRender()
                                    }
                                }

                                when (state) {
                                    is ArtListViewModel.State.Loading -> navController.navigate("wait")
                                    is ArtListViewModel.State.Success -> {
                                        state.selectedArt?.run {
                                            navController.navigate("detail")
                                        } ?: navController.navigate("list") {
                                            popUpTo(0)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
