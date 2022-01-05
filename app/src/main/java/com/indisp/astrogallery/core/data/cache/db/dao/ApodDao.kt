package com.indisp.astrogallery.core.data.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.indisp.astrogallery.core.data.cache.db.entity.ApodEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ApodDao {
    @Query("SELECT * FROM ApodEntity WHERE isFav = 1")
    fun getFavourites(): Flow<List<ApodEntity>>

    @Query("SELECT * FROM ApodEntity WHERE date = :date")
    suspend fun getDetails(date: LocalDate): ApodEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(apodEntity: ApodEntity)
}