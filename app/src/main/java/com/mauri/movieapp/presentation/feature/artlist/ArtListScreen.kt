package com.mauri.movieapp.presentation.feature.artlist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage


object ArtListScreen {

    @Composable
    fun DetailRender(
        id: String,
        state: ArtListViewModel.State.Success
    ) {

        val art = state.data.first { it.id.toString() == id }

        AsyncImage(
            contentScale = ContentScale.Crop,
            model = art.image,
            contentDescription = null
        )

        Text(text = art.title)

    }

    @Composable
    fun SuccessRender(
        state: ArtListViewModel.State.Success,
        navController: NavHostController,
        onNextPage: () -> Unit
    ) {
        LazyColumn {
            items(state.data.size, itemContent = {
                state.data[it].run {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { navController.navigate("detail/${id}") }
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Blue, CircleShape),
                            model = image,
                            contentDescription = null
                        )
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }

                    if (it == state.data.size - 1) {
                        onNextPage()
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            })
        }
    }

    @Composable
    fun LoadingRender(
        state: ArtListViewModel.State,
        navController: NavHostController
    ) {
        if (state is ArtListViewModel.State.Success) {
            navController.navigate("list") {
                popUpTo(0)
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
