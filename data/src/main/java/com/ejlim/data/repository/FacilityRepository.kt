package com.ejlim.data.repository

import com.ejlim.data.datasource.FacilityDataSource
import com.ejlim.data.service.FacilityService
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val facilityDataSource: FacilityDataSource
){
    suspend fun searchFacility(query: String) = facilityDataSource.searchFacility(query)


}