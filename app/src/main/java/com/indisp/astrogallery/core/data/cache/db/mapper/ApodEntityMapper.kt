package com.indisp.astrogallery.core.data.cache.db.mapper

import com.indisp.astrogallery.core.data.cache.db.entity.ApodEntity
import com.indisp.astrogallery.core.domain.model.Apod

interface ApodEntityMapper {
    fun toEntity(apod: Apod): ApodEntity
    fun toApod(entity: ApodEntity): Apod
}