package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ThumbnailBM

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
                        thumbnail = with(thumbnail) {
                            ThumbnailBM(
                                lqip = lqip,
                                width = width,
                                height = height,
                                altText = altText
                            )
                        }
                    )
                }
            }
    }
}