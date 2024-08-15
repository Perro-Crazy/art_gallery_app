package com.mauri.movieapp.presentation.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
object ListScreen {

    const val route = "list"

    @Composable
    fun ListRender(
        viewModel: ListViewModel,
        navController: NavHostController,
    ) {

        val state = (viewModel.state.collectAsState().value as ListViewModel.State.Success)

        Column {
            TopAppBar(
                title = {
                    Text(text = "Lista de Obras de Arte")
                }
            )

            LazyColumn {
                items(state.data.size, itemContent = {
                    state.data[it].also { art ->

                        Column(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            AsyncImage(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clickable { navController.navigate(art) }
                                    .fillMaxWidth(),
                                model = art.image,
                                contentDescription = null
                            )
                            Text(text = art.title)
                            Text(text = art.origin)
                        }

                        if (it == state.data.size - 1 && !state.errorOnNextPage) {
                            viewModel.send(ListViewModel.Event.NextPage)
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        if (it == state.data.size - 1 && state.errorOnNextPage) {
                            Button(onClick = { viewModel.send(ListViewModel.Event.NextPage) }) {
                                Text(text = "Tentar novamente")
                            }
                        }
                    }
                })
            }
        }
    }
}
