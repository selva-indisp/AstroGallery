package com.indisp.astrogallery.details.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.indisp.astrogallery.ApodApplication
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.FragmentDetailsBinding
import com.indisp.astrogallery.details.domain.model.ApodNotFount
import com.indisp.astrogallery.details.domain.model.NetworkError
import com.indisp.astrogallery.core.domain.model.Apod
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    val detailsViewModel: DetailsViewModel by lazy {
        val domainDepProvider = (requireActivity().application as ApodApplication).domainDebModule
        ViewModelProvider(
            this,
            DetailsViewModelFactory(domainDepProvider.getDetailsUseCase, domainDepProvider.getSaveAsFavouriteUseCase, domainDepProvider.getRemoveFromFavouriteUseCase)
        )[DetailsViewModel::class.java]
    }
    private val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.getDefault())
    private lateinit var viewBinding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentDetailsBinding.bind(view)
        with(viewBinding) {
            save.setOnClickListener {
                detailsViewModel.saveAsFav()
            }
            removeFromFav.setOnClickListener {
                detailsViewModel.removeFromFav()
            }
        }
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.detailsViewState.collectLatest {
                    when (it) {
                        is DetailsIdle -> handleDetailsIdleState()
                        is DetailsFound -> handleDetailsFoundState(it)
                        is DetailsNotFound -> handleDetailsNotFoundState(it)
                        is DetailsLoading -> handleDetailsLoadingState()
                        is SavedAsFavourite -> handleSavedAsFavouriteState()
                        is RemovedFromFavourite -> handleRemovedFromFavouriteState()
                        else -> Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleDetailsIdleState() {
        with(viewBinding) {
            detailsViewGroup.visibility = View.GONE
            detailsAnim.visibility = View.GONE
        }
    }

    private fun handleRemovedFromFavouriteState() {
        with(viewBinding) {
            save.visibility = View.VISIBLE
            removeFromFav.visibility = View.GONE
        }
    }

    private fun handleSavedAsFavouriteState() {
        with(viewBinding) {
            save.visibility = View.GONE
            removeFromFav.visibility = View.VISIBLE
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
        val errorAnimRawFileName = when (notFound.error) {
            is NetworkError -> R.raw.no_internet
            is ApodNotFount -> R.raw.details_not_found
            else -> R.raw.unknown_error
        }
        with(viewBinding) {
            detailsViewGroup.visibility = View.GONE
            detailsAnim.visibility = View.VISIBLE
            detailsAnim.setAnimation(errorAnimRawFileName)
            detailsAnim.playAnimation()
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
            author.text = if(apod.author.isBlank()) "\u00a9 N/A" else "\u00a9 ${apod.author}"
            title.text = apod.title
            date.text = "Shot on ${dateFormatter.format(apod.date)}"
            explanation.text = apod.explanation
            setVisibility(save, !apod.isFavourite)
            setVisibility(removeFromFav, apod.isFavourite)
            Glide.with(viewBinding.root).load(apod.url).into(viewBinding.pictureOfTheDay)
        }
    }

    private fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}