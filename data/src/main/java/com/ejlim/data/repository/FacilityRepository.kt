package com.ejlim.data.repository

import com.ejlim.data.database.AppDatabase
import com.ejlim.data.database.entity.Facility
import com.ejlim.data.datasource.FacilityDataSource
import com.ejlim.data.mapNetworkResponse
import com.ejlim.data.mapper.toFacility
import com.ejlim.data.network.NetworkResponse
import com.ejlim.data.network.onFailure
import com.ejlim.data.network.onSuccess
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val facilityDataSource: FacilityDataSource,
    private val database: AppDatabase
){
    suspend fun searchFacility(query: String): NetworkResponse<List<Facility>> {
        return facilityDataSource.searchFacility(query)
            .mapNetworkResponse { response ->
                response.data.map { item ->
                    item.toFacility()
                }
            }
    }

    fun getAllFacility() = database.facilityDao().getAllFacility()
    suspend fun insertFacility(facility: Facility){
        database.facilityDao().insertFacility(facility)
    }
}