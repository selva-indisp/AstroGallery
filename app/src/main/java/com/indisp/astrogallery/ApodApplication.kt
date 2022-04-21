package com.indisp.astrogallery

import android.app.Application
import com.indisp.astrogallery.core.data.di.DataDepModule
import com.indisp.astrogallery.core.domain.di.DomainDepModule
import com.indisp.logger.LoggerDepModule

class ApodApplication : Application() {

    private val dataDepModule: DataDepModule by lazy {
        DataDepModule(this, loggerDepModule.firebaseLogger)
    }

    val domainDebModule: DomainDepModule by lazy {
        DomainDepModule(dataDepModule.apodRepository)
    }

    val loggerDepModule: LoggerDepModule by lazy {
        LoggerDepModule()
    }
}