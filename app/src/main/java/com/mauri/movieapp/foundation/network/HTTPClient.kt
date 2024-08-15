package com.mauri.movieapp.foundation.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HTTPClient {
    fun factory(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json{
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 20000
                socketTimeoutMillis = 20000
                requestTimeoutMillis = 20000
            }

            install(HttpRequestRetry) {
                retryOnExceptionIf(maxRetries = 0) { _, cause ->
                    cause is RuntimeException
                }
            }
        }
    }
}
