package com.ejlim.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ejlim.data.database.entity.Facility
import kotlinx.coroutines.flow.Flow

@Dao
interface FacilityDao {
    @Query("SELECT * FROM facility")
    fun getAllFacility(): Flow<List<Facility>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacility(facility: Facility)
}