package com.indisp.astrogallery.core.data.remote.mapper

import com.indisp.astrogallery.core.data.remote.dto.ApodDetailsResponse
import com.indisp.astrogallery.core.domain.model.Apod
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

typealias ApodResponseMapper = (ApodDetailsResponse) -> Apod
private val incomingDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

fun apodResponseMapper(detailsDto: ApodDetailsResponse) = Apod(detailsDto.title,
    detailsDto.explanation,
    detailsDto.copyright ?: "",
    URL(detailsDto.url),
    LocalDate.parse(detailsDto.date, incomingDateFormat))