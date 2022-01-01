package com.indisp.astrogallery.favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.indisp.astrogallery.favourites.domain.usecase.GetFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(private val getFavouritesUseCase: GetFavouritesUseCase) : ViewModel() {

    private val mutableFavouritesViewState =
        MutableStateFlow<FavouritesViewState>(FavouritesLoading)
    val favouritesViewState: StateFlow<FavouritesViewState> = mutableFavouritesViewState

    fun loadFavourites() {
        viewModelScope.launch {
            mutableFavouritesViewState.value = getFavouritesUseCase().run {
                if (isEmpty())
                    return@run FavouritesEmpty
                else
                    return@run FavouritesFound(this)
            }
        }
    }
}

class FavouritesViewModelFactory(private val getFavouritesUseCase: GetFavouritesUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouritesViewModel(getFavouritesUseCase) as T
    }
}