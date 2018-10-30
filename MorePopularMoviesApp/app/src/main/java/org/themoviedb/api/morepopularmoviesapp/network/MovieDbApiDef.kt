package org.themoviedb.api.morepopularmoviesapp.network

import io.reactivex.Observable
import org.themoviedb.api.morepopularmoviesapp.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbApiDef {

    @GET("movie/popular")
    fun loadPopularMovies(@Query("api_key") apiKey: String, @Query("page") pageNumber: Int): Observable<MovieResult>


}