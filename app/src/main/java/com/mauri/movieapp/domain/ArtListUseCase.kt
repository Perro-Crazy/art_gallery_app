package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.data.model.ArticDM

class ArtListUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(): ArticDM {
        return artRepository.getAll()
    }
}