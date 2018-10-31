package org.themoviedb.api.morepopularmoviesapp.common

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.themoviedb.api.morepopularmoviesapp.network.MovieDbApi
import org.themoviedb.api.morepopularmoviesapp.repository.MovieRepository
import org.themoviedb.api.morepopularmoviesapp.viewmodel.MovieViewModel

object AppModule {

    val viewModelModule: Module = org.koin.dsl.module.module {
        viewModel { MovieViewModel(get()) }
        single { MovieRepository(get()) }
        single { MovieDbApi()}
    }
}