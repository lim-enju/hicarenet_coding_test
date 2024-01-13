package com.ejlim.data.repository

import com.ejlim.data.service.FacilityService
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val facilityService: FacilityService
){
    suspend fun searchFacility(query: String) = facilityService.searchFactility(query)


}