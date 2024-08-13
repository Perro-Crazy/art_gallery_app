package com.mauri.movieapp.domain.entity

import kotlinx.serialization.SerialName

data class ArtDM (
  @SerialName("id")
  val id: Int,
  @SerialName("title")
  val title: String,
  @SerialName("thumbnail")
  val thumbnail: ThumbnailDM,
  @SerialName("main_reference_number")
  val mainReferenceNumber: String,
  @SerialName("artist_display")
  val artistDisplay: String
)