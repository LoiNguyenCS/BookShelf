package com.example.bookshelf.data

import com.example.bookshelf.model.APIResponse
import com.example.bookshelf.network.GoogleBookAPIService

/**
 * Repository that gets data of books from googleBookApi
 */
interface BookShelfRepository {
    /** Get data of books from googleBookApi */
    suspend fun getBookData(searchedTerm: String): APIResponse
}

/**
 * Network Implementation of the Repository that gets data of books from googleBookApi
 */
class NetworkRepository (
    private val googleBookApi: GoogleBookAPIService
): BookShelfRepository {
    override suspend fun getBookData(searchedTerm: String): APIResponse = googleBookApi.searchForBook(searchedTerm)
}