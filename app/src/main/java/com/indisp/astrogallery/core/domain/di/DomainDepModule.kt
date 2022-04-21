package com.indisp.astrogallery.core.domain.di

import com.indisp.astrogallery.core.domain.usecase.GetApodDetailsUseCase
import com.indisp.astrogallery.core.domain.repository.ApodRepository
import com.indisp.astrogallery.core.domain.usecase.GetFavouritesUseCase
import com.indisp.astrogallery.core.domain.usecase.RemoveFromFavourite
import com.indisp.astrogallery.core.domain.usecase.SaveAsFavourite

class DomainDepModule(apodRepository: ApodRepository) {
    val getFavouritesUseCase = GetFavouritesUseCase(apodRepository)
    val getDetailsUseCase = GetApodDetailsUseCase(apodRepository)
    val getSaveAsFavouriteUseCase = SaveAsFavourite(apodRepository)
    val getRemoveFromFavouriteUseCase = RemoveFromFavourite(apodRepository)
}