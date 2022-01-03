package com.indisp.astrogallery.details.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var activityDetailsBinding: ActivityDetailsBinding

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
            val detailsFragment = DetailsFragment().apply {
                arguments = intent.extras
            }
            supportFragmentManager.beginTransaction().add(R.id.apodDetailsContainer, detailsFragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}