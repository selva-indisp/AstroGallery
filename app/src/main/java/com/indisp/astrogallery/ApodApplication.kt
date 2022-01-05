package com.indisp.astrogallery

import android.app.Application
import com.indisp.astrogallery.core.data.di.DataDepModule
import com.indisp.astrogallery.core.domain.di.DomainDepModule

class ApodApplication : Application() {

    private val dataDepModule: DataDepModule by lazy {
        DataDepModule(this)
    }

    val domainDebModule: DomainDepModule by lazy {
        DomainDepModule(dataDepModule.apodRepository)
    }
}