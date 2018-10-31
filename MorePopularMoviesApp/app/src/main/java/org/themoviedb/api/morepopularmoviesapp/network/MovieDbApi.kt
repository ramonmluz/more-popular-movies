package org.themoviedb.api.morepopularmoviesapp.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDbApi() {
    val service: MovieDbApiDef

    init {
        // Cria interceptor para obter poder visualizar as requisições do retrofit
        // no logCat
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Adciona o interceptor ao Okhttp
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        // Instacia o GsonBuilder para que o Json retornado seja tratado pela biblioteca Json
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                // Usa-se para o retorfit retornar objetos obseváveis
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<MovieDbApiDef>(MovieDbApiDef::class.java)
    }


    fun laodPopularMovies(pageNumber:Int): Observable<Movie>? {
        return service.loadPopularMovies("c4852d11798d35ebae996afb362875d4",
                pageNumber)
                .flatMap { movieResults -> Observable.fromIterable(movieResults.results) }
                .map { popularMovie ->
                    Movie(popularMovie.id, popularMovie.overview, popularMovie.posterPath,
                            popularMovie.originalTitle, popularMovie.voteAverage, popularMovie.releaseDate)
                }
    }

}