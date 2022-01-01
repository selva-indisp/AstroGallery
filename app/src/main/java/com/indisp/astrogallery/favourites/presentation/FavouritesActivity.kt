package com.indisp.astrogallery.favourites.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivityFavouritesBinding
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
    private val favouriteListAdapter = FavouriteListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favViewBinding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(favViewBinding.root)
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

    private fun handleFavouritesFoundState(favouritesFound: FavouritesFound) {
        favViewBinding.favouriteList.visibility = View.VISIBLE
        favouriteListAdapter.submitList(favouritesFound.favourites)
    }

    private fun handleFavouritesLoadingState() {
        favViewBinding.favouriteList.visibility = View.GONE
    }

    private fun handleFavouritesEmptyState() {

    }
}