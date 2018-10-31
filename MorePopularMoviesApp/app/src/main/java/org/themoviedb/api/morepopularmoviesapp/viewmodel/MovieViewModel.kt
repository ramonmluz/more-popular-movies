package org.themoviedb.api.morepopularmoviesapp.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.repository.MovieRepository

class MovieViewModel(val movieRepository: MovieRepository) : ViewModel() {

    fun loadMovies(): Observable<Movie>? {
        return movieRepository.loadPopularMovies()
    }
}