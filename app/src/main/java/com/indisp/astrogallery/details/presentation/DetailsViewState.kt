package com.indisp.astrogallery.details.presentation

import com.indisp.astrogallery.details.domain.model.GetApodDetailsError
import com.indisp.astrogallery.favourites.domain.model.Apod

sealed class DetailsViewState
data class DetailsFound(val apod: Apod): DetailsViewState()
data class DetailsNotFound(val error: GetApodDetailsError): DetailsViewState()
object DetailsLoading : DetailsViewState()
