package com.indisp.astrogallery.core.data.cache

import com.indisp.astrogallery.core.data.cache.db.dao.ApodDao
import com.indisp.astrogallery.core.data.cache.db.mapper.ApodEntityMapper
import com.indisp.astrogallery.core.domain.model.Apod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ApodCacheServiceImpl(private val dao: ApodDao, private val mapper: ApodEntityMapper) : ApodCacheService {
    override fun getFavourites(): Flow<List<Apod>> = dao.getFavourites().map { favs -> favs.map { mapper.toApod(it) } }

    override suspend fun getDetailsFor(date: LocalDate): Apod? {
        return dao.getDetails(date)?.let {
            mapper.toApod(it)
        }
    }

    override suspend fun saveDetails(apod: Apod) {
        dao.save(mapper.toEntity(apod))
    }
}