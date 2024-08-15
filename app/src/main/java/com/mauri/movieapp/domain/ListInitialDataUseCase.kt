package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ArtContainerBM

class ListInitialDataUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(): ArtContainerBM {
        return artRepository.get().let { container ->
            ArtContainerBM(
                currentPage = container.pagination.currentPage,
                totalPages = container.pagination.totalPages,
                data = container.data.filter { ArtBM.valid(it) }.map { ArtBM.from(it) }
            )
        }
    }
}