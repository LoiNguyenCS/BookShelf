package com.example.bookshelf.fake

import com.example.bookshelf.model.APIResponse
import com.example.bookshelf.model.BookInfo
import com.example.bookshelf.model.ImageOption
import com.example.bookshelf.model.SpecificBookInfo
import kotlin.random.Random

object FakeDataSource {
    val listOfFakeBookInfo = listOf(
        getFakeBookInfo(),
        getFakeBookInfo(),
        getFakeBookInfo(),
    )
    val googleBookAPIService =  APIResponse(listOfFakeBookInfo)
}

fun getFakeBookInfo() = BookInfo(getFakeSpecificBookInfo())
fun getFakeSpecificBookInfo() = SpecificBookInfo(title = generateRandomString(), description = generateRandomString(),  imageLinks = getFakeImageLinks())
fun getFakeImageLinks() = ImageOption(generateRandomString(), generateRandomString())


/**
 * This method create a random string that has a length in range 5-15. The characters in the resultant string are in the set of alphabetical characters and numbers.
 */
fun generateRandomString(): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val length = Random.nextInt(5, 15)
    return (1..length)
        .map { characters.random() }
        .joinToString("")
}