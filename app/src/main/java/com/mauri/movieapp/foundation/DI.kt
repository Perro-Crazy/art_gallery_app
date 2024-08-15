package com.mauri.movieapp.foundation

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.domain.ArtListUseCase
import com.mauri.movieapp.foundation.network.HTTPClient
import com.mauri.movieapp.presentation.feature.list.ListViewModel
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
                ListViewModel(get(), get())
            }
        },
        module {
            factory {
                ArtListUseCase(get())
            }
        },
        module {
            factory {
                ArtRepository(get())
            }
        }
    )
}
