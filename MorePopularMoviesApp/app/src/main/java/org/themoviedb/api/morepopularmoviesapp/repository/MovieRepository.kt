package org.themoviedb.api.morepopularmoviesapp.repository

import io.reactivex.Observable
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.network.MovieDbApi

class MovieRepository(val movieDbApi: MovieDbApi) {

    var pageNumber: Int = 1

    fun loadPopularMovies(isNextPage: Boolean, isGenericError: Boolean): Observable<Movie>? {

        if (isNextPage && !isGenericError) {
            pageNumber++
        }
        return movieDbApi.laodPopularMovies(pageNumber)
    }

//    fun searchNextPage(isNextPage: Boolean): Observable<Movie>? {
//
//        if (isNextPage) {
//            pageNumber++
//        }
//        return movieDbApi.laodPopularMovies(pageNumber)
//    }

}