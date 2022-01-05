package com.indisp.astrogallery.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indisp.astrogallery.details.presentation.DetailsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class SearchViewModel(private val detailsViewModel: DetailsViewModel) : ViewModel() {

    private val mutableSelectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = mutableSelectedDate

    fun loadDetailsForDate(date: LocalDate) {
        mutableSelectedDate.value = date
        detailsViewModel.loadDetailsFor(mutableSelectedDate.value!!)
    }

    fun loadDetailsForNextDate() {
        mutableSelectedDate.value?.let {
            if (it.isEqual(LocalDate.now()))
                return@let
            loadDetailsForDate(it.plusDays(1))
        }
    }

    fun loadDetailsForPrevDate() {
        mutableSelectedDate.value?.let {
            loadDetailsForDate(it.minusDays(1))
        }
    }
}

class SearchViewModelFactory(private val detailsViewModel: DetailsViewModel) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(detailsViewModel) as T
    }
}