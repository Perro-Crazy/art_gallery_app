package com.mauri.movieapp.data

import com.mauri.movieapp.data.model.ArtContainerDM
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url

class ArtRepository(
    private val httpClient: HttpClient
) {
    suspend fun get(page: Int?): ArtContainerDM {
        return httpClient.prepareGet {
            url("https://api.artic.edu/api/v1/artworks")
            parameter("limit", "50")
            parameter("fields", "id,title,artist_display,main_reference_number,image_id")
            page?.also { parameter("page", it) }
        }.execute().body<ArtContainerDM>()
    }
}
