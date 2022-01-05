package com.indisp.astrogallery.core.data.cache.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateTypeConverter {

    @TypeConverter
    fun fromString(value: String): LocalDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    @TypeConverter
    fun fromDate(value: LocalDate): String = value.toString()
}