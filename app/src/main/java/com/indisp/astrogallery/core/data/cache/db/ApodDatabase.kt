package com.indisp.astrogallery.core.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.indisp.astrogallery.core.data.cache.db.converter.LocalDateTypeConverter
import com.indisp.astrogallery.core.data.cache.db.dao.ApodDao
import com.indisp.astrogallery.core.data.cache.db.entity.ApodEntity

@Database(entities = [ApodEntity::class], version = 1)
@TypeConverters(LocalDateTypeConverter::class)
abstract class ApodDatabase : RoomDatabase() {
    companion object {
        const val NAME = "apod_db"
    }
    abstract fun apodDao(): ApodDao
}