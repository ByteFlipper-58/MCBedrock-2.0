package com.byteflipper.mcbedrock

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

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

suspend fun fetchChangelogEntries(type: String): List<ChangelogEntry> = withContext(Dispatchers.IO) {
    val url = when (type) {
        "bedrockPatchNotes" -> "https://launchercontent.mojang.com/v2/bedrockPatchNotes.json"
        "javaPatchNotes" -> "https://launchercontent.mojang.com/v2/javaPatchNotes.json"
        "dungeonsPatchNotes" -> "https://launchercontent.mojang.com/v2/dungeonsPatchNotes.json"
        "legendsPatchNotes" -> "https://launchercontent.mojang.com/v2/legendsPatchNotes.json"
        else -> throw IllegalArgumentException("Unknown patch notes type: $type")
    }

    with(URL(url).openConnection() as HttpURLConnection) {
        requestMethod = "GET"
        inputStream.bufferedReader().use {
            val response = it.readText()
            val json = Json { ignoreUnknownKeys = true }
            val changelogsResponse = json.decodeFromString<ChangelogsResponse>(response)
            return@withContext changelogsResponse.entries
        }
    }
}
