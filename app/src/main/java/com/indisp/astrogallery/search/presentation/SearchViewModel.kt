package com.indisp.astrogallery.search.presentation

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import com.indisp.astrogallery.details.presentation.DetailsViewModel
import java.util.*

class SearchViewModel(private val detailsViewModel: DetailsViewModel) : ViewModel() {

    private var currentDate: Date? = null

    fun loadDetailsForDate(date: Date) {
        currentDate = date
        detailsViewModel.loadDetailsFor(currentDate!!)
    }

    fun loadDetailsForNextDate() {
        loadDetailsForDate(Calendar.getInstance(Locale.getDefault()).run {
            time = currentDate
            add(Calendar.DATE, 1)
            return@run time
        })
    }

    fun loadDetailsForPrevDate() {
        loadDetailsForDate(Calendar.getInstance(Locale.getDefault()).run {
            time = currentDate

            return@run time
        })
    }
}