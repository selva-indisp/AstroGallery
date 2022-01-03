package com.indisp.astrogallery.favourites.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.util.*

@Parcelize
data class Apod(val title: String, val explanation: String, val author: String, val url: URL, val date: Date) : Parcelable {
}
