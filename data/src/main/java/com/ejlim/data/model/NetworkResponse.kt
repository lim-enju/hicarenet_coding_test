package com.ejlim.data.model

import java.io.IOException

sealed class NetworkResponse<out T : Any> {
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    data class Failure(val code: Int, val msg: String?) : NetworkResponse<Nothing>()

    data class NetworkError(val exception: IOException, val msg: String) : NetworkResponse<Nothing>()

    data class Unexpected(val t: Throwable?, val msg: String) : NetworkResponse<Nothing>()
}

inline fun <T : Any> NetworkResponse<T>.onSuccess(
    action: (value: T?) -> Unit
): NetworkResponse<T> {
    if (this is NetworkResponse.Success) action(body)
    return this
}

inline fun <T : Any> NetworkResponse<T>.onFailure(
    action: (t: Throwable, msg: String) -> Unit
): NetworkResponse<T> {
    when(this){
        is NetworkResponse.Success -> return this
        is NetworkResponse.Failure -> action(Throwable(), msg?:"")
        is NetworkResponse.NetworkError -> action(exception, msg?:"")
        is NetworkResponse.Unexpected -> action(t?: Throwable(), msg?:"")
    }
    return this
}