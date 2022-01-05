package com.indisp.astrogallery.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.model.Result
import com.indisp.astrogallery.core.domain.usecase.GetApodDetailsUseCase
import com.indisp.astrogallery.core.domain.usecase.RemoveFromFavourite
import com.indisp.astrogallery.core.domain.usecase.SaveAsFavourite
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailsViewModel(
    private val getApodDetailsUseCase: GetApodDetailsUseCase,
    private val saveAsFavourite: SaveAsFavourite,
    private val removeFromFavourite: RemoveFromFavourite
) : ViewModel() {

    private val mutableDetailsViewState = MutableStateFlow<DetailsViewState>(DetailsIdle)
    private var detailsLoadJob: Job? = null
    private var currentDetails: Apod? = null
    val detailsViewState: StateFlow<DetailsViewState> = mutableDetailsViewState

    fun loadDetailsFor(date: LocalDate) {
        mutableDetailsViewState.value = DetailsLoading
        detailsLoadJob?.cancel()
        detailsLoadJob = viewModelScope.launch {
            val detailsResult = getApodDetailsUseCase(date)
            mutableDetailsViewState.value = when (detailsResult) {
                is Result.Success -> {
                    currentDetails = detailsResult.data
                    DetailsFound(detailsResult.data)
                }
                is Result.Failed -> DetailsNotFound(detailsResult.error)
            }
        }
    }

    fun saveAsFav() {
        currentDetails?.run {
            viewModelScope.launch {
                mutableDetailsViewState.value = if(saveAsFavourite(this@run)) {
                    SavedAsFavourite
                } else {
                    FailedToSaveAsFavourite
                }
            }
        }
    }

    fun removeFromFav() {
        currentDetails?.run {
            viewModelScope.launch {
                mutableDetailsViewState.value = if(removeFromFavourite(this@run)) {
                    RemovedFromFavourite
                } else {
                    FailedToRemoveFromFavourite
                }
            }
        }
    }
}

class DetailsViewModelFactory(
    private val getApodDetailsUseCase: GetApodDetailsUseCase,
    private val saveAsFavourite: SaveAsFavourite,
    private val removeFromFavourite: RemoveFromFavourite
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(getApodDetailsUseCase, saveAsFavourite, removeFromFavourite) as T
    }
}