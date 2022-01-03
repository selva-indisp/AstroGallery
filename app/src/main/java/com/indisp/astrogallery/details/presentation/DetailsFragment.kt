package com.indisp.astrogallery.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.FragmentDetailsBinding
import com.indisp.astrogallery.details.domain.model.ApodNotFount
import com.indisp.astrogallery.details.domain.model.GetApodDetailsError
import com.indisp.astrogallery.details.domain.model.NetworkError
import com.indisp.astrogallery.details.domain.usecase.GetApodDetailsUseCase
import com.indisp.astrogallery.favourites.domain.model.Apod
import com.indisp.astrogallery.favourites.domain.usecase.GetFavouritesUseCase
import com.indisp.astrogallery.favourites.presentation.FavouritesViewModel
import com.indisp.astrogallery.favourites.presentation.FavouritesViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

     companion object {
        const val KEY_APOD = "key_apod"
    }

    private val detailsViewModel: DetailsViewModel by lazy {
        ViewModelProvider(this, DetailsViewModelFactory(GetApodDetailsUseCase()))[DetailsViewModel::class.java]
    }
    private val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    private lateinit var viewBinding: FragmentDetailsBinding
    private lateinit var apod: Apod

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentDetailsBinding.bind(view)
        apod = arguments?.get(KEY_APOD) as Apod
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.detailsViewState.collectLatest {
                    when(it) {
                        is DetailsFound -> handleDetailsFoundState(it)
                        is DetailsNotFound -> handleDetailsNotFoundState(it)
                        is DetailsLoading -> handleDetailsLoadingState()
                    }
                }
            }
        }
    }

    private fun handleDetailsFoundState(detailsFound: DetailsFound) {
        with(viewBinding) {
            detailsAnim.visibility = View.GONE
            detailsViewGroup.visibility = View.VISIBLE
        }
        updateDetails(detailsFound.apod)
    }

    private fun handleDetailsNotFoundState(notFound: DetailsNotFound) {
        val errorAnimRawFileName = when(notFound.error) {
            is NetworkError -> R.raw.no_internet
            is ApodNotFount -> R.raw.details_not_found
            else -> R.raw.unknown_error
        }
        with(viewBinding) {
            detailsViewGroup.visibility = View.GONE
            detailsAnim.visibility = View.VISIBLE
            detailsAnim.setAnimation(errorAnimRawFileName)
        }
    }

    private fun handleDetailsLoadingState() {
        with(viewBinding) {
            detailsViewGroup.visibility = View.GONE
            detailsAnim.visibility = View.VISIBLE
            detailsAnim.setAnimation(R.raw.details_searching)
        }
    }

    private fun updateDetails(apod: Apod) {
        with(viewBinding) {
            author.text = "\u00a9 ${apod.author}"
            title.text = apod.title
            date.text = "Shot on ${dateFormatter.format(apod.date)}"
            explanation.text = apod.explanation
            Glide.with(viewBinding.root).load(apod.url).into(viewBinding.pictureOfTheDay)
        }
    }
}