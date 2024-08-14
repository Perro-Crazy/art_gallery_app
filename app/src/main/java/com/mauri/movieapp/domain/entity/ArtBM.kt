package com.mauri.movieapp.domain.entity

import com.mauri.movieapp.data.model.ArtDM


data class ArtBM(
    val id: Int,
    val title: String,
    val image: String,
    val mainReferenceNumber: String,
    val artistDisplay: String,
    val description: String,
    val origin: String
) {
    companion object {
        fun valid(input: ArtDM): Boolean {
            return with(input) {
                !title.isNullOrBlank() &&
                !imageId.isNullOrBlank() &&
                !description.isNullOrBlank() &&
                !origin.isNullOrBlank()
            }
        }
        fun from(input: ArtDM): ArtBM {
            return with(input) {
                ArtBM(
                    id = id,
                    title = checkNotNull(title),
                    mainReferenceNumber = mainReferenceNumber,
                    artistDisplay = artistDisplay,
                    description = checkNotNull(description),
                    origin = checkNotNull(origin),
                    image = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
                )
            }
        }
    }
}