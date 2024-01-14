package com.ejlim.data.datasource

import com.ejlim.data.database.AppDatabase
import com.ejlim.data.database.entity.Facility
import javax.inject.Inject

class FacilityLocalDataSource @Inject constructor(
    private val database: AppDatabase
){
    fun getAllFacility() = database.facilityDao().getAllFacility()
    fun insertFacility(facility: Facility) =
        database.facilityDao().insertFacility(facility)

}