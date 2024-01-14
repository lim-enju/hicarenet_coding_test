package com.ejlim.data

import com.ejlim.data.model.NetworkResponse

suspend fun <T : Any, R : Any> NetworkResponse<T>.mapNetworkResponse(getData: suspend (T) -> R): NetworkResponse<R> =
    when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(getData(body))
        is NetworkResponse.Failure -> NetworkResponse.Failure(code, msg)
        is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(exception, msg)
        is NetworkResponse.Unexpected -> NetworkResponse.Unexpected(t, msg)
    }
