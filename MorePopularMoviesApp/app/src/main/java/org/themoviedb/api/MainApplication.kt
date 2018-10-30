package org.themoviedb.api

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.themoviedb.api.morepopularmoviesapp.common.AppModule

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(AppModule.viewModelModule))
    }
}