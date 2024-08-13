package com.mauri.movieapp.presentation.feature.artlist

import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest


object ArtListScreen {
    @Composable
    fun Render(state: ArtListViewModel.State) {
        when (state) {
            is ArtListViewModel.State.Loading -> LoadingRender()
            is ArtListViewModel.State.Success -> SuccessRender(state)
        }
    }

    @Composable
    private fun SuccessRender(state: ArtListViewModel.State.Success) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.data.size, itemContent = {
                state.data[it].run {
                    Text(text = title)
                    AsyncImage(
                        model = image,
                        contentDescription = null
                    )
                }
            })
        }
    }

    @Composable
    private fun LoadingRender() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
