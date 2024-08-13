package com.mauri.movieapp.domain.entity

import kotlinx.serialization.SerialName

data class ThumbnailDM (
  @SerialName("lqip")
  val lqip: String,
  @SerialName("width")
  val width: Int,
  @SerialName("height")
  val height: Int,
  @SerialName("alt_text")
  val altText: String
)
