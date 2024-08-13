package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM

class ArtListUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(): List<ArtBM> {
        return artRepository
            .getAll()
            .data
            .map {
                with(it) {
                    ArtBM(
                        id = id,
                        title = title,
                        mainReferenceNumber = mainReferenceNumber,
                        artistDisplay = artistDisplay,
                        image = "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg"
                    )
                }
            }
    }
}