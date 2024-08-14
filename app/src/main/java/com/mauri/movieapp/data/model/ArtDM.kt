package com.mauri.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtDM (
  @SerialName("id")
  val id: Int,
  @SerialName("title")
  val title: String,
  @SerialName("image_id")
  val imageId: String?,
  @SerialName("main_reference_number")
  val mainReferenceNumber: String,
  @SerialName("artist_display")
  val artistDisplay: String,
  @SerialName("description")
  val description:  String?,
  @SerialName("place_of_origin")
  val origin:  String?
)