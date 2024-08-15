package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ArtContainerBM

class ListInitialDataUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(): ArtContainerBM {
        return with(artRepository.get()) {
            ArtContainerBM(
                currentPage = pagination.currentPage,
                totalPages = pagination.totalPages,
                data = data.filter { ArtBM.valid(it) }.map { ArtBM.from(it) }
            )
        }
    }
}