package com.indisp.astrogallery.core.data.repository

import com.indisp.shared.domain.Result
import com.indisp.astrogallery.core.data.cache.ApodCacheService
import com.indisp.astrogallery.core.data.remote.ApodApiService
import com.indisp.astrogallery.core.data.remote.mapper.ApodResponseMapper
import com.indisp.astrogallery.details.domain.model.ApodNotFount
import com.indisp.astrogallery.details.domain.model.GenericError
import com.indisp.astrogallery.details.domain.model.GetApodDetailsError
import com.indisp.astrogallery.details.domain.model.NetworkError
import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class ApodRepositoryImpl(
    private val apiService: ApodApiService,
    private val cacheService: ApodCacheService,
    private val responseMapper: ApodResponseMapper
) : ApodRepository {
    override fun getFavourites(): Flow<List<Apod>> = cacheService.getFavourites()

    override suspend fun getDetailsFor(date: LocalDate): Result<Apod, GetApodDetailsError> = try {
        cacheService.getDetailsFor(date)?.run {
            Result.Success(this)
        } ?: run {
            val apiResponse = apiService.getDetailsFor(date.toString())
            if (apiResponse.mediaType == "image") {
                val details = responseMapper(apiService.getDetailsFor(date.toString()))
                cacheService.saveDetails(details)
                Result.Success(details)
            } else {
                Result.Failed(GenericError)
            }
        }
    } catch (ioException: IOException) {
        Result.Failed(NetworkError)
    } catch (httpException: HttpException) {
        if (httpException.code() == 404)
            Result.Failed(ApodNotFount)
        Result.Failed(GenericError)
    } catch (throwable: Throwable) {
        Result.Failed(GenericError)
    }

    override suspend fun saveDetails(apod: Apod): Boolean =
        try {
            cacheService.saveDetails(apod)
            true
        } catch (throwable: Throwable) {
            false
        }

}