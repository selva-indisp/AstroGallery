package com.indisp.astrogallery.search.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.indisp.astrogallery.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var searchViewBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchViewBinding.root)
        setSupportActionBar(searchViewBinding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}