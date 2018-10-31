package org.themoviedb.api.morepopularmoviesapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_datail.*
import org.themoviedb.api.morepopularmoviesapp.R
import org.themoviedb.api.morepopularmoviesapp.model.Movie

class MovieDatailActivity : AppCompatActivity() {

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_datail)

        val intent: Intent = this.getIntent()
        movie = intent.getSerializableExtra(Intent.EXTRA_INITIAL_INTENTS) as Movie

        loadImage()
        supportActionBar?.title = movie.originalTitle
        releaseDate.text = movie.releaseDate
        overview.text = movie.overview
    }

    fun loadImage() {
        val imageUrl: String = getString(R.string.base_url_image) + movie.posterPath
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
