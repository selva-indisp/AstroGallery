package com.indisp.astrogallery.core.domain.usecase

import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.repository.ApodRepository

data class RemoveFromFavourite(private val apodRepository: ApodRepository) {
    suspend operator fun invoke(apod: Apod): Boolean {
        return apodRepository.saveDetails(apod.copy(isFavourite = false))
    }
}
