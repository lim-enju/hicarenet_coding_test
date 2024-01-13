package com.ejlim.data.datasource

import com.ejlim.data.BuildConfig
import com.ejlim.data.model.response.FacilityItem
import com.ejlim.data.model.response.FacilityListResponse
import com.ejlim.data.service.FacilityService
import javax.inject.Inject
import kotlin.random.Random

class FacilityDataSource @Inject constructor(
    private val facilityService: FacilityService
){
    fun searchFacility(query: String): Result<FacilityListResponse?>{
        val isSuccess = Random.nextBoolean()

        //응답이 실패했다고 가정
        if(!isSuccess) return Result.failure(Throwable())

        //query가 이름에 포함된 시설 생성
        val list = List(10){ index ->
            val facilityName = "${query}${index}"
            val facilityId =  Integer.toHexString(facilityName.hashCode())

            FacilityItem(
                facilityId = facilityId,
                facilityName = facilityName,
                logoUrl = "${BuildConfig.BASE_URL}/$facilityId"
            )
        }

        //response 임의 생성
       val response = FacilityListResponse(
            200,
            "success",
            list
        )

        //응답 성공
        return Result.success(response)
    }
}