package com.example.bookshelf.fake

import com.example.bookshelf.data.BookShelfRepository
import com.example.bookshelf.model.APIResponse

class FakeNetworkBookRepository: BookShelfRepository {
    override suspend fun getBookData(searchedTerm: String): APIResponse {
        return FakeDataSource.googleBookAPIService
    }
}