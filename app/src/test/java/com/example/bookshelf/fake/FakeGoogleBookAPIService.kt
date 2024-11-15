package com.example.bookshelf.fake

import com.example.bookshelf.model.APIResponse
import com.example.bookshelf.network.GoogleBookAPIService

class FakeGoogleBookAPIService: GoogleBookAPIService {
    override suspend fun searchForBook(term: String): APIResponse {
        return FakeDataSource.googleBookAPIService
    }
}