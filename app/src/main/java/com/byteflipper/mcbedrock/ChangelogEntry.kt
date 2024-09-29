package com.byteflipper.mcbedrock

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangelogImage(
    val title: String,
    val url: String? = null,
    val drawableResId: Int? = null,
)

@Serializable
data class ChangelogEntry(
    val title: String,
    val version: String,
    @SerialName("patchNoteType") val patchNoteType: String? = null,
    val date: String,
    val image: ChangelogImage,
    val contentPath: String,
    val id: String,
    val shortText: String
)

@Serializable
data class ChangelogsResponse(
    val version: Int,
    val entries: List<ChangelogEntry>
)
