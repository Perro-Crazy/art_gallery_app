package com.mauri.movieapp.domain.entity

data class ArtBM (
  val id: Int,
  val title: String,
  val thumbnail: ThumbnailBM,
  val mainReferenceNumber: String,
  val artistDisplay: String
)