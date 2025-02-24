package com.example.bookshelf.network

import com.example.bookshelf.model.APIResponse
import com.github.loinguyencs.safeinternetaccesschecker.effect.HasRiskyInternetConnection
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookAPIService {
    @GET("volumes")
    @HasRiskyInternetConnection
    suspend fun searchForBook(@Query("q") term: String): APIResponse

}