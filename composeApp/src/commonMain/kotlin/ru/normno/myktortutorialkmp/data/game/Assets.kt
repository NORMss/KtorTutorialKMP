package ru.normno.myktortutorialkmp.data.game

import kotlinx.serialization.Serializable

@Serializable
data class Assets(
    val coverUrl: String? = null,
    val iconUrl: String? = null,
    val tileUrl: String? = null,
)