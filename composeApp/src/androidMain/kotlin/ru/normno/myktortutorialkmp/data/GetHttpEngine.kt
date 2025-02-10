package ru.normno.myktortutorialkmp.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpEngine(): HttpClientEngine {
    return OkHttp.create()
}