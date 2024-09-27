package com.byteflipper.mcbedrock

import kotlinx.serialization.Serializable

@Serializable
data class NewsEntry(
    val title: String,
    val category: String,
    val date: String,
    val text: String,
    val playPageImage: Image,
    val newsPageImage: Image,
    val readMoreLink: String,
    val newsType: List<String>,
    val id: String
)

@Serializable
data class Image(
    val title: String,
    val url: String,
    val dimensions: Dimensions? = null
)

@Serializable
data class Dimensions(
    val width: Int,
    val height: Int
)

@Serializable
data class NewsResponse(
    val entries: List<NewsEntry>
)