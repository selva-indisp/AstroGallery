package com.indisp.astrogallery.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.indisp.astrogallery.Result
import com.indisp.astrogallery.details.domain.usecase.GetApodDetailsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailsViewModel(private val getApodDetailsUseCase: GetApodDetailsUseCase) : ViewModel() {

    private val mutableDetailsViewState = MutableStateFlow<DetailsViewState>(DetailsIdle)
    private var detailsLoadJob: Job? = null
    val detailsViewState: StateFlow<DetailsViewState> = mutableDetailsViewState

    fun loadDetailsFor(date: LocalDate) {
        mutableDetailsViewState.value = DetailsLoading
        detailsLoadJob?.cancel()
        detailsLoadJob = viewModelScope.launch {
            val detailsResult = getApodDetailsUseCase(date)
            mutableDetailsViewState.value = when (detailsResult) {
                is Result.Success -> DetailsFound(detailsResult.data)
                is Result.Failed -> DetailsNotFound(detailsResult.error)
            }
        }
    }
}

class DetailsViewModelFactory(private val getApodDetailsUseCase: GetApodDetailsUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(getApodDetailsUseCase) as T
    }
}