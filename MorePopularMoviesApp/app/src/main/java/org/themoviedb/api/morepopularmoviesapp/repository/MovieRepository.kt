package org.themoviedb.api.morepopularmoviesapp.repository

import io.reactivex.Observable
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.network.MovieDbApi

class MovieRepository (val movieDbApi: MovieDbApi) {


    fun loadPopularMovies(): Observable<Movie>?{
         return movieDbApi.laodPopularMovies()
    }

}