package com.ejlim.data.network

import com.ejlim.data.Constants.API_ERROR
import com.ejlim.data.Constants.COMMON_RESULT_FAIL_ERROR
import com.ejlim.data.Constants.NULL_BODY_ERROR
import com.ejlim.data.Constants.UNKNOWN_ERROR
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResponseCall<T : Any>(
    private val delegate: Call<T>,
) : Call<NetworkResponse<T>> {

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        return delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        if((body as BaseResponse<*>).statusCode == 200){
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Success(body))
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Failure(COMMON_RESULT_FAIL_ERROR, "result fail"))
                            )
                        }
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Failure(NULL_BODY_ERROR, "null body"))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            error
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Failure(API_ERROR, errorBody.toString()))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Failure(UNKNOWN_ERROR,
                                "unknown exception occurred"))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val networkResponse: NetworkResponse<T> = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable,
                        throwable.message ?: "io exception occurred")
                    else -> NetworkResponse.Unexpected(throwable,
                        throwable.message ?: "unknown exception occurred")
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<T>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}