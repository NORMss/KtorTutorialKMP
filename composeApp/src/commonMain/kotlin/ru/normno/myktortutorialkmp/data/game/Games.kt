package ru.normno.myktortutorialkmp.data.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Games(
    @SerialName("data")
    val games: List<Game> = emptyList(),
    val pagination: Pagination
)