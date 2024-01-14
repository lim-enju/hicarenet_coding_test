package com.ejlim.data.network

data class BaseResponse<T> (
    val statusCode:Int,
    val msg:String,
    val data: T
)