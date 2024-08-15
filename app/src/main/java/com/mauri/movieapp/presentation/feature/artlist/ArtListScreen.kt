package com.mauri.movieapp.presentation.feature.artlist

import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
object ArtListScreen {

    @Composable
    fun DetailRender(
        state: ArtListViewModel.State.Success,
        onBack: () -> Unit,
        idArt: Int
    ) {
        state.data[idArt].run {
            Column {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBack()
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft, contentDescription = null)
                        }
                    }
                )

                BackHandler {
                    onBack()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp) ,
                                contentScale = ContentScale.Crop,
                                model = image,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Author: $title",
                                fontSize = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Artist display: $artistDisplay",
                                fontSize = 15.sp
                            )
                        }
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Row {
                            AndroidView(
                                factory = { context -> TextView(context) },
                                update = {
                                    it.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun ListRender(
        state: ArtListViewModel.State.Success,
        onNextPage: () -> Unit,
        onSelectItem: (Int) -> Unit
    ) {

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
                                    .clickable { onSelectItem(it) }
                                    .fillMaxWidth(),
                                model = art.image,
                                contentDescription = null
                            )
                            Text(text = art.title)
                            Text(text = art.origin)
                        }

                        if (it == state.data.size - 1 && !state.errorOnNextPage) {
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

                        if (it == state.data.size - 1 && state.errorOnNextPage) {
                            Button(onClick = { onNextPage() }) {
                                Text(text = "Tentar novamente")
                            }
                        }
                    }
                })
            }
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

        if (state is ArtListViewModel.State.ErrorOnInitialLoad) {
            navController.navigate("error") {
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
