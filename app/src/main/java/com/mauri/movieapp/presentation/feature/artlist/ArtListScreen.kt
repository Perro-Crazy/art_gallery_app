package com.mauri.movieapp.presentation.feature.artlist

import android.widget.TextView
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mauri.movieapp.presentation.model.ArtVM


object ArtListScreen {

    @Composable
    fun DetailRender(
        state: ArtListViewModel.State.Success
    ) {

        with(checkNotNull(state.selectedArt)) {
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

    @Composable
    fun SuccessRender(
        state: ArtListViewModel.State.Success,
        onNextPage: () -> Unit,
        onSelectItem: (ArtVM) -> Unit
    ) {
        LazyColumn {
            items(state.data.size, itemContent = {
                state.data[it].run {
                    Row(
                        modifier = Modifier
                            .clickable { onSelectItem(this) }
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
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
                            Column(
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text(text = title)
                                Text(text = origin)
                            }
                        }
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
