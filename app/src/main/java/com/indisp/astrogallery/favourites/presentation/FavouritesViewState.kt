package com.indisp.astrogallery.favourites.presentation

import com.indisp.astrogallery.favourites.domain.model.Apod

sealed class FavouritesViewState
data class FavouritesFound(val favourites: List<Apod>): FavouritesViewState()
object FavouritesLoading: FavouritesViewState()
object FavouritesEmpty: FavouritesViewState()
