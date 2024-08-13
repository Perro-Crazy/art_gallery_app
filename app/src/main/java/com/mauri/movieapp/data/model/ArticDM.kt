package com.mauri.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticDM (
  @SerialName("data")
  val data: List<ArtDM>,
)