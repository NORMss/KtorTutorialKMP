package ru.normno.myktortutorialkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform