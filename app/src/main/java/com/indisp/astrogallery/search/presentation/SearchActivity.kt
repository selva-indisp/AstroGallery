package com.indisp.astrogallery.search.presentation

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivitySearchBinding
import com.indisp.astrogallery.details.presentation.DetailsFragment
import com.indisp.astrogallery.details.presentation.DetailsViewModel
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var searchViewBinding: ActivitySearchBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchViewBinding.root)
        setUpActionBar()
        setUpSearchView()
        if (savedInstanceState == null) {
            addDetailsFragment()
        }
        supportFragmentManager.executePendingTransactions()
        detailsViewModel =
            (supportFragmentManager.findFragmentByTag(DetailsFragment::class.simpleName) as DetailsFragment).detailsViewModel
        detailsViewModel.loadDetailsFor(Date())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpActionBar() {
        setSupportActionBar(searchViewBinding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpSearchView() {
        with(searchViewBinding.searchView) {
            searchDate.setOnClickListener {
                showDatePickerDialog()
            }
        }
    }

    private fun addDetailsFragment() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.apodDetailsContainer,
                DetailsFragment(),
                DetailsFragment::class.simpleName
            ).commit()
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.show()
    }
}