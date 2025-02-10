package ru.normno.myktortutorialkmp.data.game

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val status: Int,
    val apiStatus: Int,
    val assets: Assets,
    val dateModified: String,
    val slug: String
)