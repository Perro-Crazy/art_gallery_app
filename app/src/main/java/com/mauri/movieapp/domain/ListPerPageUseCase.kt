package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ArtContainerBM

class ListPerPageUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(parameter: Parameter): ArtContainerBM {
        val nextPage = parameter.currentPage + 1

        parameter.run {
            if (currentPage == totalPages) return ArtContainerBM(
                data = emptyList(),
                currentPage = currentPage,
                totalPages = totalPages
            )
        }

        return with(artRepository.get(nextPage)){
            ArtContainerBM(
                currentPage = pagination.currentPage,
                totalPages = pagination.totalPages,
                data = data.filter { ArtBM.valid(it) }.map { ArtBM.from(it) }
            )
        }
    }

    data class Parameter(
        val totalPages: Int,
        val currentPage: Int,
    )
}