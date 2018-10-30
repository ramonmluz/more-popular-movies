package org.themoviedb.api.morepopularmoviesapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.themoviedb.api.morepopularmoviesapp.R
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var movies: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = ArrayList<Movie> ()


        textMovie.visibility = View.GONE
        progress.visibility = View.VISIBLE

        movieViewModel.movieRepository.loadPopularMovies()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ movie ->
                    (movies as ArrayList<Movie>).add(movie)
                }, { error ->
                    error.printStackTrace()
                })

// movieViewModel.movieRepository.loadPopularMovies()
//                ?.subscribeOn(Schedulers.newThread())
//                ?.subscribe({ movie ->
//                    movie.originalTitle
//                }, {
//                    error -> error.printStackTrace()
//                })

        textMovie.visibility = View.VISIBLE
        progress.visibility = View.GONE
        Log.v("Testes", movies.toString())

//        movieViewModel.movieRepository.loadPopularMovies()?.subscribeOn(Schedulers.newThread())?.subscribe(object : DisposableObserver<Movie>() {
//            override fun onComplete() {
//                textMovie.visibility = View.VISIBLE
//                progress.visibility = View.GONE
//
//                Toast.makeText(application, "Error teste onComplete ", Toast.LENGTH_LONG)
//            }
//
//            override fun onNext(t: Movie) {
//                textMovie.visibility = View.VISIBLE
//                progress.visibility = View.GONE
//
//
//                Toast.makeText(application, "Error teste " + t.originalTitle, Toast.LENGTH_LONG)
//            }
//
//            override fun onError(e: Throwable) {
//
//                textMovie.visibility = View.VISIBLE
//                progress.visibility = View.GONE
//
//                Toast.makeText(application, "Error teste", Toast.LENGTH_LONG)
//            }
//        },  { e ->  e.printStackTrace() })


//        api.loadMovies()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({ movie ->
//                    movies.add("${movie.title} -- ${movie.episodeId}")
//                }, { e ->
//                    e.printStackTrace()
//                },{
//                    movieAdapter.notifyDataSetChanged()
//                })

    }
}
