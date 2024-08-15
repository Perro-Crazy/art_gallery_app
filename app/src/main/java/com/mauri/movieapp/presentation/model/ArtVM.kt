package com.mauri.movieapp.presentation.model

import android.os.Parcelable
import com.mauri.movieapp.domain.entity.ArtBM
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ArtVM(
  val id: Int,
  val title: String,
  val image: String,
  val mainReferenceNumber: String,
  val artistDisplay: String,
  val description: String,
  val origin: String
): Parcelable {
  companion object {
    fun from(input: ArtBM): ArtVM {
      return with(input) {
        ArtVM(
          id = id,
          title = title,
          mainReferenceNumber = mainReferenceNumber,
          artistDisplay = artistDisplay,
          description = description,
          origin = origin,
          image = image
        )
      }
    }
  }
}