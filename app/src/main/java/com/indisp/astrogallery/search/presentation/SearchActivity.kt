package com.indisp.astrogallery.search.presentation

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivitySearchBinding
import com.indisp.astrogallery.details.presentation.DetailsFragment
import kotlinx.android.synthetic.main.fragment_search_view.*
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class SearchActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var searchViewBinding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel

    private companion object {
        val TAG = SearchActivity::class.simpleName
    }

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
        val detailsViewModel =
            (supportFragmentManager.findFragmentByTag(DetailsFragment::class.simpleName) as DetailsFragment).detailsViewModel
        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(detailsViewModel)).get(SearchViewModel::class.java)
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
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.selectedDate.collectLatest {
                    it?.run {
                        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
                        with(searchViewBinding.searchView) {
                            searchDate.text = format(dateFormatter)
                            nextDate.isEnabled = isBefore(LocalDate.now())
                            prevDate.isEnabled = true
                        }
                    }
                }
            }
        }
        with(searchViewBinding.searchView) {
            searchDate.setOnClickListener {
                searchViewModel.selectedDate.let { it ->
                    val date = it.value ?: LocalDate.now()
                    DatePickerDialog(this@SearchActivity, this@SearchActivity, date.year , date.monthValue - 1, date.dayOfMonth).also {dialog ->
                        dialog.datePicker.maxDate = System.currentTimeMillis()
                    }.show()
                }
            }
            nextDate.setOnClickListener {
                searchViewModel.loadDetailsForNextDate()
            }
            prevDate.setOnClickListener {
                searchViewModel.loadDetailsForPrevDate()
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet: $dayOfMonth, $month, $year")
        val date = LocalDate.of(year, month+1, dayOfMonth)
        searchViewModel.loadDetailsForDate(date)
    }
}