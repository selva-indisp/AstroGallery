package com.indisp.astrogallery.details.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivityDetailsBinding
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var activityDetailsBinding: ActivityDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    companion object {
        const val KEY_APOD = "key_apod"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(activityDetailsBinding.root)
        setSupportActionBar(activityDetailsBinding.toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.apodDetailsContainer,
                DetailsFragment(),
                DetailsFragment::class.simpleName
            ).commit()
        }
        supportFragmentManager.executePendingTransactions()
        detailsViewModel = (supportFragmentManager.findFragmentByTag(DetailsFragment::class.simpleName) as DetailsFragment).detailsViewModel
        detailsViewModel.loadDetailsFor(intent.extras!!.getSerializable(KEY_APOD) as Date)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}