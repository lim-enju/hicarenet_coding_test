package com.ejlim.data.datasource

import com.ejlim.data.BuildConfig
import com.ejlim.data.StringUtil
import com.ejlim.data.model.response.Facility
import com.ejlim.data.model.response.FacilityListResponse
import com.ejlim.data.service.FacilityService
import javax.inject.Inject
import kotlin.random.Random

class FacilityDataSource @Inject constructor(
    private val facilityService: FacilityService
){
    fun searchFacility(query: String): FacilityListResponse?{
        val isSuccess = Random.nextBoolean()

        //응답이 실패했다고 가정
        if(!isSuccess) return null

        //query가 이름에 포함된 시설 생성
        val list = List(10){ index ->
            val facilityId = StringUtil.getRandomString(20)
            Facility(
                facilityId = facilityId,
                facilityName = "${query}${index}",
                logoUrl = "${BuildConfig.BASE_URL}/facilityId"
            )
        }

        //response 임의 생성
       return FacilityListResponse(
            200,
            "success",
            list
        )

    }
}