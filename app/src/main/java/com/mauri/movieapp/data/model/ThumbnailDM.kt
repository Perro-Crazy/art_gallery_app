package com.mauri.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
