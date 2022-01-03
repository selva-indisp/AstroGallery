package com.indisp.astrogallery.favourites.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivityFavouritesBinding
import com.indisp.astrogallery.details.presentation.DetailsActivity
import com.indisp.astrogallery.details.presentation.DetailsFragment
import com.indisp.astrogallery.favourites.domain.model.Apod
import com.indisp.astrogallery.favourites.domain.usecase.GetFavouritesUseCase
import kotlinx.coroutines.flow.collectLatest

class FavouritesActivity : AppCompatActivity() {
    private companion object {
        val TAG = FavouritesActivity::class.simpleName
    }
    private lateinit var favViewBinding: ActivityFavouritesBinding
    private val favouritesViewModel: FavouritesViewModel by lazy {
        ViewModelProvider(this, FavouritesViewModelFactory(GetFavouritesUseCase())).get(FavouritesViewModel::class.java)
    }
    private val favouriteListAdapter = FavouriteListAdapter(::launchApodDetailsActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favViewBinding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(favViewBinding.root)
        setSupportActionBar(favViewBinding.toolbar)
        with(favViewBinding.favouriteList) {
            layoutManager = LinearLayoutManager(this@FavouritesActivity)
            adapter = favouriteListAdapter
        }
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesViewModel.favouritesViewState.collectLatest {
                    when(it) {
                        is FavouritesLoading -> handleFavouritesLoadingState()
                        is FavouritesEmpty -> handleFavouritesEmptyState()
                        is FavouritesFound -> handleFavouritesFoundState(it)
                    }
                }
            }
        }
        favouritesViewModel.loadFavourites()
    }

    private fun launchApodDetailsActivity(apod: Apod) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsFragment.KEY_APOD, apod)
        startActivity(intent)
    }

    private fun handleFavouritesFoundState(favouritesFound: FavouritesFound) {
        toggleFavouritesListGroupVisibility(View.VISIBLE)
        toggleFavouritesEmptyViewGroupVisibility(View.GONE)
        toggleFavouritesLoaderVisibility(View.GONE)

        favouriteListAdapter.submitList(favouritesFound.favourites)
    }

    private fun handleFavouritesLoadingState() {
        toggleFavouritesListGroupVisibility(View.GONE)
        toggleFavouritesEmptyViewGroupVisibility(View.GONE)
        toggleFavouritesLoaderVisibility(View.VISIBLE)

    }

    private fun handleFavouritesEmptyState() {
        toggleFavouritesListGroupVisibility(View.GONE)
        toggleFavouritesEmptyViewGroupVisibility(View.VISIBLE)
        toggleFavouritesLoaderVisibility(View.GONE)
    }

    private fun toggleFavouritesLoaderVisibility(visibility: Int) {
        with(favViewBinding) {
            favouritesLoading.visibility = visibility
        }
    }

    private fun toggleFavouritesListGroupVisibility(visibility: Int) {
        with(favViewBinding) {
            favouriteList.visibility = visibility
            searchFab.visibility = visibility
            appBarLayout.visibility = visibility
        }
    }

    private fun toggleFavouritesEmptyViewGroupVisibility(visibility: Int) {
        with(favViewBinding) {
            favouritesEmptyViewGroup.visibility = visibility
        }
    }
}