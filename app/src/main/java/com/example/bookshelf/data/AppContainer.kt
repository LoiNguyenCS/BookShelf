package com.example.bookshelf.data

import com.example.bookshelf.network.GoogleBookAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    // Create a Json instance with ignoreUnknownKeys set to true
    private val json: Json = Json {
        ignoreUnknownKeys = true  // This will ignore any unknown fields in the JSON response
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: GoogleBookAPIService by lazy {
        retrofit.create(GoogleBookAPIService::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        NetworkRepository(retrofitService)
    }

}