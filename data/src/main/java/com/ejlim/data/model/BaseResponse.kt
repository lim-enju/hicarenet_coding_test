package com.ejlim.data.model

data class BaseResponse<T> (
    val statusCode:Int,
    val msg:String,
    val data: T
)