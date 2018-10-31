package org.themoviedb.api.morepopularmoviesapp.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import butterknife.OnClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.themoviedb.api.morepopularmoviesapp.R
import org.themoviedb.api.morepopularmoviesapp.adapter.MovieAdapter
import org.themoviedb.api.morepopularmoviesapp.model.Movie
import org.themoviedb.api.morepopularmoviesapp.viewmodel.MovieViewModel

class MainActivity : BaseApplication() {

    private val movieViewModel: MovieViewModel by viewModel()
    var movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textMsgErroView.text = getString(R.string.error_listing_movies);
        initRecyclerView()
        loadMovies()
    }

    fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MovieAdapter(movies, this)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                var moviePosition: Int = parent!!.getChildLayoutPosition(view)

                if (moviePosition == 0 || moviePosition == 1) {
                    outRect?.top = getAnIntDp(8);
                    defineMarginBottom(outRect)
                } else {
                    defineMarginBottom(outRect)
                }

                if (moviePosition % 2 == 0) {
                    defineMargin(outRect, 8, 4)
                } else {
                    defineMargin(outRect, 4, 8)
                }

            }
        })
    }

    fun defineMarginBottom(rect: Rect?) {
        rect?.bottom = getAnIntDp(8)
    }

    fun defineMargin(rect: Rect?, marginLeft: Int, marginRight: Int) {
        rect?.left = getAnIntDp(marginLeft)
        rect?.right = getAnIntDp(marginRight)
    }

    fun getAnIntDp(value: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), resources.displayMetrics).toInt()
    }

    fun loadMovies() {
        showView(progress)
        subscribe(movieViewModel.loadMovies()
        !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->
                    // Chamado no sucesso da obtenção dos dos
                    movies.add(movie)
                }, {
                    // Chamado quando erro é apresentado
                    showError()
                }, {
                    // Chamado quando toda a chamada é completa
                    showMovies()
                })
        )
    }

    fun showView(view: View) {
        recyclerView.visibility = View.GONE
        progress.visibility = View.GONE
        areaErro.visibility = View.GONE
        view?.visibility = View.VISIBLE
    }

    fun showMovies() {
        showView(recyclerView)
        recyclerView.adapter.notifyDataSetChanged()
    }

    fun showError() {
        showView(areaErro)
    }

    @OnClick(R.id.areaErro)
    fun reloadMovies() {
        loadMovies()
    }


}
