package com.mauri.movieapp.presentation.common

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> SavedStateHandle.getMutableStateFlow(key: String, default: T): MutableStateFlow<T> {
    return MutableStateFlow(getStateFlow(key, default).value)
}
