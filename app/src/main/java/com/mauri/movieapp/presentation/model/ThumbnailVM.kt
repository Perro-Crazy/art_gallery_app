package com.mauri.movieapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailVM (
  val lqip: String,
  val width: Int,
  val height: Int,
  val altText: String
): Parcelable
