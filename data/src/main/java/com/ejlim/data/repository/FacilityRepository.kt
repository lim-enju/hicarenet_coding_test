package com.ejlim.data.repository

import com.ejlim.data.database.AppDatabase
import com.ejlim.data.database.entity.Facility
import com.ejlim.data.datasource.FacilityLocalDataSource
import com.ejlim.data.datasource.FacilityRemoteDataSource
import com.ejlim.data.mapNetworkResponse
import com.ejlim.data.mapper.toFacility
import com.ejlim.data.model.NetworkResponse
import com.ejlim.data.model.response.FacilityListResponse
import com.ejlim.data.retry
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val facilityRemoteDataSource: FacilityRemoteDataSource,
    private val facilityLocalDataSource: FacilityLocalDataSource
){
    suspend fun searchFacility(query: String): NetworkResponse<List<Facility>>
        = retry(2) {
            facilityRemoteDataSource.searchFacility(query)
        }.mapNetworkResponse { response ->
            response.data.map { item ->
                item.toFacility()
            }
        }


    fun getAllFacility() = facilityLocalDataSource.getAllFacility()
    fun insertFacility(facility: Facility) = facilityLocalDataSource.insertFacility(facility)

}