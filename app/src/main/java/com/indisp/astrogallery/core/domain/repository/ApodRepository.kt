package com.indisp.astrogallery.core.domain.repository

import com.indisp.shared.domain.Result
import com.indisp.astrogallery.details.domain.model.GetApodDetailsError
import com.indisp.astrogallery.core.domain.model.Apod
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ApodRepository {
    fun getFavourites(): Flow<List<Apod>>
    suspend fun getDetailsFor(date: LocalDate): Result<Apod, GetApodDetailsError>
    suspend fun saveDetails(apod: Apod): Boolean
}