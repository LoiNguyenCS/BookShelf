package com.example.bookshelf.data

import com.example.bookshelf.model.APIResponse
import com.example.bookshelf.network.GoogleBookAPIService
import com.github.loinguyencs.safeinternetaccesschecker.effect.HasRiskyInternetConnection

/**
 * Repository that gets data of books from googleBookApi
 */
interface BookShelfRepository {
    /** Get data of books from googleBookApi */
    @HasRiskyInternetConnection
    suspend fun  getBookData(searchedTerm: String): APIResponse
}

/**
 * Network Implementation of the Repository that gets data of books from googleBookApi
 */
class NetworkRepository (
    private val googleBookApi: GoogleBookAPIService
): BookShelfRepository {
    @HasRiskyInternetConnection
    override suspend fun getBookData(searchedTerm: String): APIResponse = googleBookApi.searchForBook(searchedTerm)
}