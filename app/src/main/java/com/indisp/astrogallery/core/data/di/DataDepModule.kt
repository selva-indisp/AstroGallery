package com.indisp.astrogallery.core.data.di

import android.content.Context
import androidx.room.Room
import com.indisp.astrogallery.core.data.cache.ApodCacheService
import com.indisp.astrogallery.core.data.cache.ApodCacheServiceImpl
import com.indisp.astrogallery.core.data.cache.db.ApodDatabase
import com.indisp.astrogallery.core.data.cache.db.mapper.ApodEntityMapperImpl
import com.indisp.astrogallery.core.data.remote.ApodApiService
import com.indisp.astrogallery.core.data.remote.mapper.apodResponseMapper
import com.indisp.astrogallery.core.data.repository.ApodRepositoryImpl
import com.indisp.astrogallery.core.domain.repository.ApodRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataDepModule(private val context: Context) {

    private val httpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor{
        val newUrl = it.request().url.newBuilder().addQueryParameter("api_key", "xEgCEOA0ZVJZrJggT05YFedW0MkrEGoYwsFTqsUX").build()
        it.proceed(it.request().newBuilder().url(newUrl).build())
    }.addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    private val apodDatabase: ApodDatabase by lazy {
        Room.databaseBuilder(context, ApodDatabase::class.java, ApodDatabase.NAME).build()
    }

    private val apodApiService: ApodApiService by lazy {
        retrofit.create(ApodApiService::class.java)
    }

    private val apodCacheService: ApodCacheService by lazy {
        ApodCacheServiceImpl(apodDatabase.apodDao(), ApodEntityMapperImpl())
    }

    val apodRepository: ApodRepository by lazy {
        ApodRepositoryImpl(apodApiService, apodCacheService, ::apodResponseMapper)
    }
}