package com.indisp.astrogallery.core.data.cache.db.mapper

import com.indisp.astrogallery.core.data.cache.db.entity.ApodEntity
import com.indisp.astrogallery.core.domain.model.Apod
import java.net.URL

class ApodEntityMapperImpl : ApodEntityMapper {
    override fun toEntity(apod: Apod): ApodEntity = ApodEntity(apod.date, apod.title, apod.explanation, apod.url.toString(), apod.isFavourite, apod.author)

    override fun toApod(entity: ApodEntity): Apod = Apod(entity.title, entity.desc, entity.author, URL(entity.path), entity.date, entity.isFav)
}