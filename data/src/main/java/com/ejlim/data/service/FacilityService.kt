package com.ejlim.data.service

import com.ejlim.data.model.response.FacilityListResponse
import retrofit2.http.GET

interface FacilityService {
    @GET("all/facility")
    suspend fun searchFactility(query: String): FacilityListResponse
}