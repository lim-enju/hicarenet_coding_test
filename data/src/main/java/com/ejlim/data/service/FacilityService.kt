package com.ejlim.data.service

import com.ejlim.data.model.response.FacilityListResponse
import com.ejlim.data.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FacilityService {
    @GET("all/facility")
    suspend fun searchFactility(@Query("query") query: String): NetworkResponse<FacilityListResponse>
}