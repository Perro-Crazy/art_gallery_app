package com.mauri.movieapp.foundation

import com.mauri.movieapp.foundation.network.HTTPClient
import org.koin.dsl.module

object DI {
    val modules = listOf(
        module {
            single {
                HTTPClient.factory()
            }
        },
        module {

        }
    )
}
