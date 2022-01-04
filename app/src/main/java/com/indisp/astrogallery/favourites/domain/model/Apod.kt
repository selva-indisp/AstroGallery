package com.indisp.astrogallery.favourites.domain.model

import java.net.URL
import java.time.LocalDate
import java.util.*

data class Apod(val title: String, val explanation: String, val author: String, val url: URL, val date: LocalDate)
