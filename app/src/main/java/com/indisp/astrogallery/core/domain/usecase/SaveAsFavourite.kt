package com.indisp.astrogallery.core.domain.usecase

import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.repository.ApodRepository

data class SaveAsFavourite(val apodRepository: ApodRepository) {
    suspend operator fun invoke(apod: Apod): Boolean {
        return apodRepository.saveDetails(apod.copy(isFavourite = true))
    }
}
