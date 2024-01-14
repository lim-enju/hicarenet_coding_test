package com.ejlim.data.datasource

import com.ejlim.data.BuildConfig
import com.ejlim.data.model.response.FacilityItem
import com.ejlim.data.model.response.FacilityListResponse
import com.ejlim.data.model.NetworkResponse
import com.ejlim.data.service.FacilityService
import javax.inject.Inject
import kotlin.random.Random

class FacilityDataSource @Inject constructor(
    private val facilityService: FacilityService
){
    fun searchFacility(query: String): NetworkResponse<FacilityListResponse> {
        //80%확률로 응답이 성공했다고 가정함
        val isSuccess = Random.nextInt(100) < 80

        //응답이 실패했다고 가정
        if(!isSuccess) return NetworkResponse.Failure(400, "데이터 로딩에 실패했습니다.")

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
        return NetworkResponse.Success(response)
    }
}