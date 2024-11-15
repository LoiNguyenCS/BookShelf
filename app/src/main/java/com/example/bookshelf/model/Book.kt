package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class APIResponse (
    val items: List<BookInfo>
)

@Serializable
data class BookInfo (
    val volumeInfo: SpecificBookInfo
)

@Serializable
data class SpecificBookInfo (
    val title: String? = null,
    val description: String? =  null,
    val imageLinks: ImageOption? = null,
)

@Serializable
data class ImageOption (
    val smallThumbnail: String,
    val thumbnail: String,
)
