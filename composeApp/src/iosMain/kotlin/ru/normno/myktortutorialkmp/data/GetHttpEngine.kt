package ru.normno.myktortutorialkmp.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngine(): HttpClientEngine {
    return Darwin.create()
}