package com.indisp.astrogallery.details.domain.model

sealed class GetApodDetailsError
object NetworkError : GetApodDetailsError()
object GenericError : GetApodDetailsError()
object ApodNotFount : GetApodDetailsError()
