package com.ejlim.data

import com.ejlim.data.model.NetworkResponse
import kotlinx.coroutines.delay

suspend fun <T : Any, R : Any> NetworkResponse<T>.mapNetworkResponse(getData: suspend (T) -> R): NetworkResponse<R> =
    when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(getData(body))
        is NetworkResponse.Failure -> NetworkResponse.Failure(code, msg)
        is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(exception, msg)
        is NetworkResponse.Unexpected -> NetworkResponse.Unexpected(t, msg)
    }

suspend fun <T: NetworkResponse<Any>> retry(
    numberOfRetries: Int,
    delayBetweenRetries: Long = 100,
    block: suspend() -> T
): T {
    repeat(numberOfRetries) {
        val result = block()
        if(result is NetworkResponse.Success<*> || result is NetworkResponse.Failure){
            return result
        }
        delay(delayBetweenRetries)
    }
    return block()
}