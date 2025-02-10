package ru.normno.myktortutorialkmp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.SerializationException
import ru.normno.myktortutorialkmp.result_and_error.Error
import ru.normno.myktortutorialkmp.result_and_error.RemoteErrorWithCode
import ru.normno.myktortutorialkmp.result_and_error.Result

class KtorApiClient(
    val httpClient: HttpClient,
) {
    suspend inline fun <reified R : Any> get(
        route: String,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): Result<R, Error> {
        return saveCall {
            httpClient.get {
                url("$baseUrl/$route")

                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }

                headers.forEach { (key, value) ->
                    header(key, value)
                }

            }
        }
    }

    suspend inline fun <reified R : Any> post(
        route: String,
        body: Map<String, Any> = emptyMap(),
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): Result<R, Error> {
        return saveCall {
            httpClient.post {
                url("$baseUrl/$route")


                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }

                setBody(body)

                headers.forEach { (key, value) ->
                    header(key, value)
                }

            }
        }
    }


    suspend inline fun <reified R> saveCall(
        execute: () -> HttpResponse,
    ): Result<R, Error> {
        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.NO_INTERNET_ERROR)
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.SERIALIZATION_ERROR)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            return Result.Error(
                RemoteErrorWithCode(Error.Remote.UNKNOWN)
            )
        }
        return responseToResult(response)
    }

    suspend inline fun <reified R> responseToResult(response: HttpResponse): Result<R, Error> {
        return when (response.status.value) {
            in 200..299 -> Result.Success(
                response.body<R>()
            )

            in 300..308 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.REDIRECTION_ERROR,
                    response.status.value,
                )
            )

            in 400..499 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.CLIENT_ERROR,
                    response.status.value,
                )
            )

            in 500..599 -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.SERVER_ERROR,
                    response.status.value,
                )
            )

            else -> Result.Error(
                RemoteErrorWithCode(
                    Error.Remote.UNKNOWN,
                    response.status.value,
                )
            )
        }
    }


    val baseUrl = "https://api.curseforge.com"
}