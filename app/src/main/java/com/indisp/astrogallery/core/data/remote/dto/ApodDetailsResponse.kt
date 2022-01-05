package com.indisp.astrogallery.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApodDetailsResponse(val url: String, val date: String, val title: String, val explanation: String, val copyright: String?, @SerializedName("media_type")val mediaType: String?)
