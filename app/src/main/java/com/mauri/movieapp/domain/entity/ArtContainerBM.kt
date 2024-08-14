package com.mauri.movieapp.domain.entity

data class ArtContainerBM (
  val data: List<ArtBM>,
  val totalPages: Int,
  val currentPage: Int,
)
