package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ArtContainerBM

class ArtListUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(parameter: Parameter? = null): ArtContainerBM {
        val nextPage = parameter?.currentPage?.let { it + 1 }
        return artRepository.get(nextPage).let { container ->
            ArtContainerBM(
                currentPage = container.pagination.currentPage,
                totalPages = container.pagination.currentPage,
                data = container.data
                    .filter { it.imageId != null }
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
            )
        }
    }

    data class Parameter(
        val totalPages: Int,
        val currentPage: Int,
    )
}