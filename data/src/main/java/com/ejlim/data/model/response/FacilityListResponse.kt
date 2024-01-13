package com.ejlim.data.model.response

data class FacilityListResponse (
    val statusCode: Int,
    val message: String,
    val data: List<FacilityItem>
)

data class FacilityItem(
    val facilityId: String,
    val facilityName: String,
    val logoUrl: String
)