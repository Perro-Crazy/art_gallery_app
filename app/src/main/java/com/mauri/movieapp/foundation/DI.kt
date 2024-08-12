package com.mauri.movieapp.foundation

import com.mauri.movieapp.foundation.network.HTTPClient
import com.mauri.movieapp.presentation.feature.movielist.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DI {
    val modules = listOf(
        module {
            single {
                HTTPClient.factory()
            }
        },
        module {
            viewModel {
                MovieListViewModel()
            }
        }
    )
}
