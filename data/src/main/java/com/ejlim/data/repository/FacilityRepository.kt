package com.ejlim.data.repository

import com.ejlim.data.database.AppDatabase
import com.ejlim.data.database.entity.Facility
import com.ejlim.data.datasource.FacilityDataSource
import com.ejlim.data.service.FacilityService
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val facilityDataSource: FacilityDataSource,
    private val database: AppDatabase
){
    suspend fun searchFacility(query: String): List<Facility> {
        val data = facilityDataSource.searchFacility(query)
        return data.getOrNull()?.data?.map {
            Facility(it.facilityId, it.facilityName, it.logoUrl)
        }?: listOf()
    }

    fun getAllFacility() = database.facilityDao().getAllFacility()
    suspend fun insertFacility(facility: Facility){
        database.facilityDao().insertFacility(facility)
    }
}