package com.mauri.movieapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtVM (
  val id: Int,
  val title: String,
  val thumbnail: ThumbnailVM,
  val mainReferenceNumber: String,
  val artistDisplay: String
): Parcelable