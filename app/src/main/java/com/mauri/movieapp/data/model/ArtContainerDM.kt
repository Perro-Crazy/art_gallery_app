package com.mauri.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtContainerDM (
  @SerialName("data")
  val data: List<ArtDM>,
  @SerialName("pagination")
  val pagination: PaginationDM,
)