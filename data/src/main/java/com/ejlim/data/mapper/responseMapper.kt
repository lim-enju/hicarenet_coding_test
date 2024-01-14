package com.ejlim.data.mapper

import com.ejlim.data.database.entity.Facility
import com.ejlim.data.model.response.FacilityItem

fun FacilityItem.toFacility() = Facility(facilityId, facilityName, logoUrl)