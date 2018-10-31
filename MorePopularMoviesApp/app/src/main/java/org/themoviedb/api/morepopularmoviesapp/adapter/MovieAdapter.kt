package org.themoviedb.api.morepopularmoviesapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_movie_item.view.*
import org.themoviedb.api.morepopularmoviesapp.R
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.ui.MovieDatailActivity
import java.security.AccessController.getContext

class MovieAdapter(val items: List<Movie>, val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = items.get(position)
        val imageUrl: String = context.getString(R.string.base_url_image) + movie.posterPath

        Picasso.with(context).load(imageUrl).placeholder(R.mipmap.local_movies).error(R.mipmap.ic_launcher)
                .into(holder.imageView, object : Callback {
                    override fun onSuccess() {
                    }

                    override fun onError() {
                        Toast.makeText(context, "An error ocorred ", Toast.LENGTH_SHORT).show()
                    }

                })

        holder.imageView.setOnClickListener { view ->
            var context: Context = view.context
            var intent: Intent = Intent(view.context, MovieDatailActivity::class.java)
            intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, movie)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.movieImageGrid
    }

}
