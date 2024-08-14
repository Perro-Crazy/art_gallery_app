package com.mauri.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDM (
  @SerialName("total_pages")
  val totalPages: Int,
  @SerialName("current_page")
  val currentPage: Int,
)
