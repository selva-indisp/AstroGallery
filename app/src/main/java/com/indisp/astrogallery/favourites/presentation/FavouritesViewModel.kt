package com.indisp.astrogallery.favourites.presentation

import android.text.TextUtils.isEmpty
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.usecase.GetFavouritesUseCase
import com.indisp.astrogallery.core.domain.usecase.RemoveFromFavourite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val removeFromFavourite: RemoveFromFavourite
) : ViewModel() {

    private val mutableFavouritesViewState =
        MutableStateFlow<FavouritesViewState>(FavouritesLoading)
    val favouritesViewState: StateFlow<FavouritesViewState> = mutableFavouritesViewState

    fun loadFavourites() {
        viewModelScope.launch {
            getFavouritesUseCase().collectLatest {
                if (it.isEmpty())
                    mutableFavouritesViewState.value = FavouritesEmpty
                else
                    mutableFavouritesViewState.value = FavouritesFound(it)
            }
        }
    }

    fun removeFromFavourites(apod: Apod) {
        viewModelScope.launch {
            if (removeFromFavourite(apod)) {
                loadFavourites()
            } else {
                mutableFavouritesViewState.value = FailedToRemoveFromFavourites
            }
        }
    }
}

class FavouritesViewModelFactory(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val removeFromFavourite: RemoveFromFavourite
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouritesViewModel(getFavouritesUseCase, removeFromFavourite) as T
    }
}