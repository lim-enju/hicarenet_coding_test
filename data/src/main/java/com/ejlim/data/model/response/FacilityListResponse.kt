package com.ejlim.data.model.response

data class FacilityListResponse (
    val statusCode: Int,
    val message: String,
    val data: List<Facility>
)

data class Facility(
    val facilityId: String,
    val facilityName: String,
    val logoUrl: String
)