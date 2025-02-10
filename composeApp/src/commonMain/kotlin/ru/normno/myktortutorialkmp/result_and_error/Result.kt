package ru.normno.myktortutorialkmp.result_and_error

sealed class Result<D, E : Error>(
    val data: D? = null,
    val error: ru.normno.myktortutorialkmp.result_and_error.Error? = null,
) {
    class Success<D>(
        data: D?
    ) : Result<D, ru.normno.myktortutorialkmp.result_and_error.Error>(data)

    class Error<D>(
        error: ru.normno.myktortutorialkmp.result_and_error.Error
    ) : Result<D, ru.normno.myktortutorialkmp.result_and_error.Error>(null, error)
}