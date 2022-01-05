package com.indisp.astrogallery.core.data.cache.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class ApodEntity(@PrimaryKey val date: LocalDate, val title: String, val desc: String, val path: String, val isFav: Boolean, val author: String)