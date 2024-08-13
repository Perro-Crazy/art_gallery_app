package com.mauri.movieapp.data

import com.mauri.movieapp.domain.entity.ArtDM
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArtRepository(
    private val httpClient: HttpClient
) {
    suspend fun getAll(): List<ArtDM> {
        return httpClient.get(
            "https://api.artic.edu/api/v1/artworks?fields=id,title,artist_display,main_reference_number,thumbnail"
        ).body<List<ArtDM>>()
    }
}
