package ru.normno.myktortutorialkmp.data.game

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val index: Int,
    val pageSize: Int,
    val resultCount: Int,
    val totalCount: Int
)