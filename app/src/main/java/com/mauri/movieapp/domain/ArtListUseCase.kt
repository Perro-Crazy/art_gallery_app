package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.entity.ArtBM
import com.mauri.movieapp.domain.entity.ArtContainerBM

class ArtListUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(parameter: Parameter? = null): ArtContainerBM {
        val nextPage = parameter?.currentPage?.let { it + 1 }

        parameter?.run {
            if (currentPage == totalPages) return ArtContainerBM(
                data = emptyList(),
                currentPage = parameter.currentPage,
                totalPages = parameter.totalPages
            )
        }

        return artRepository.get(nextPage).let { container ->
            ArtContainerBM(
                currentPage = container.pagination.currentPage,
                totalPages = container.pagination.totalPages,
                data = container.data.filter { ArtBM.valid(it) }.map { ArtBM.from(it) }
            )
        }
    }

    data class Parameter(
        val totalPages: Int,
        val currentPage: Int,
    )
}