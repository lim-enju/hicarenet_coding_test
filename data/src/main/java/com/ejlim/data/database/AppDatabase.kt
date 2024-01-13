package com.ejlim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ejlim.data.database.dao.FacilityDao
import com.ejlim.data.database.entity.Facility

@Database(entities = [Facility::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun facilityDao(): FacilityDao
}