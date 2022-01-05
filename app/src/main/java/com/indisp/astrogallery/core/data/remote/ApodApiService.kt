package com.indisp.astrogallery.core.data.remote

import com.indisp.astrogallery.core.data.remote.dto.ApodDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    @GET("planetary/apod")
    suspend fun getDetailsFor(@Query("date") date: String): ApodDetailsResponse
}