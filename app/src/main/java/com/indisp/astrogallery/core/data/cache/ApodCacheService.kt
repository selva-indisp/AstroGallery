package com.indisp.astrogallery.core.data.cache

import com.indisp.astrogallery.core.domain.model.Apod
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ApodCacheService {
    fun getFavourites(): Flow<List<Apod>>
    suspend fun getDetailsFor(date: LocalDate): Apod?
    suspend fun saveDetails(apod: Apod)
}