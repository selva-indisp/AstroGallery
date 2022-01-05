package com.indisp.astrogallery.core.domain.model

import java.net.URL
import java.time.LocalDate

data class Apod(
    val title: String,
    val explanation: String,
    val author: String,
    val url: URL,
    val date: LocalDate,
    val isFavourite: Boolean = false
)