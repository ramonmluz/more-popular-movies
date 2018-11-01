package org.themoviedb.api.morepopularmoviesapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_datail.*
import kotlinx.android.synthetic.main.activity_movie_datail.view.*
import org.themoviedb.api.morepopularmoviesapp.R
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.util.Util

class MovieDatailActivity : AppCompatActivity() {

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_datail)

        val intent: Intent = this.getIntent()
        movie = intent.getSerializableExtra(Intent.EXTRA_INITIAL_INTENTS) as Movie

        loadImage()
        toolbarMovieDetail.setSubtitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbarMovieDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar?.title = movie.originalTitle
        collapsingToolbar?.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))

        releaseDateDatail.text = Util.getReleaseYear(movie.releaseDate)
        overviewDetail.text = movie.overview
    }

    fun loadImage() {
        val imageUrl: String = getString(R.string.base_url_image) + movie.backdropPath
        Picasso.with(this).load(imageUrl).placeholder(R.mipmap.local_movies).error(R.mipmap.ic_launcher)
                .into(movieImageDetail, object : Callback {
                    override fun onSuccess() {
                    }

                    override fun onError() {
                        Toast.makeText(applicationContext, "An error ocorred ", Toast.LENGTH_SHORT).show()
                    }

                })
    }
}
