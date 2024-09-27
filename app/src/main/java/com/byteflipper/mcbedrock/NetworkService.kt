package com.byteflipper.mcbedrock

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchNewsEntries(): List<NewsEntry> = withContext(Dispatchers.IO) {
    val url = URL("https://launchercontent.mojang.com/v2/news.json")
    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"
        inputStream.bufferedReader().use {
            val response = it.readText()
            val json = Json { ignoreUnknownKeys = true }
            val newsResponse = json.decodeFromString<NewsResponse>(response)
            return@withContext newsResponse.entries
        }
    }
}
