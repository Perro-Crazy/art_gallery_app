package com.mauri.movieapp.presentation.feature.detail

import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import com.mauri.movieapp.presentation.model.ArtVM

@OptIn(ExperimentalMaterial3Api::class)
object DetailScreen {
    @Composable
    fun Render(
        art: ArtVM,
        navController: NavHostController
    ) {

        with(art) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = title) },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            ) { padding ->

                Column(modifier = Modifier.padding(padding)) {

                    BackHandler {
                        navController.popBackStack()
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
                                        .height(300.dp),
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
                                        it.text = HtmlCompat.fromHtml(
                                            description,
                                            HtmlCompat.FROM_HTML_MODE_COMPACT
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}
