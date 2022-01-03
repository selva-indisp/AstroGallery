package com.indisp.astrogallery.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.indisp.astrogallery.R
import com.indisp.astrogallery.databinding.FragmentDetailsBinding
import com.indisp.astrogallery.favourites.domain.model.Apod
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

     companion object {
        const val KEY_APOD = "key_apod"
    }

    private val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    private lateinit var viewBinding: FragmentDetailsBinding
    private lateinit var apod: Apod

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentDetailsBinding.bind(view)
        apod = arguments?.get(KEY_APOD) as Apod
        with(viewBinding) {
            author.text = "\u00a9 ${apod.author}"
            title.text = apod.title
            date.text = "Shot on ${dateFormatter.format(apod.date)}"
            explanation.text = apod.explanation
            Glide.with(viewBinding.root).load(apod.url).into(viewBinding.pictureOfTheDay)
        }
    }
}