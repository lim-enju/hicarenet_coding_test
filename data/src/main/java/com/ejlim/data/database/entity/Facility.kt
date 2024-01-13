package com.ejlim.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Facility (
    @PrimaryKey
    @ColumnInfo(name = "facility_id")
    val facilityId: String,

    @ColumnInfo(name = "facility_name")
    val facilityName: String,

    @ColumnInfo(name = "logoUrl")
    val logoUrl: String
)