package org.themoviedb.api.morepopularmoviesapp.ui

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AbsListView
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
    private lateinit var layoutManager: LinearLayoutManager
    private var movies = mutableListOf<Movie>()
    private var moviesAux = mutableListOf<Movie>()
    private var scrollOutItems: Int = 0
    private var isScrolling: Boolean = false
    private var isNextPage: Boolean = false
    private var isGenericError: Boolean = false
    private var isLastPage: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textMsgErroView.text = getString(R.string.error_listing_movies)
        initRecyclerView()
        loadMovies()
    }

    fun initRecyclerView() {
        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MovieAdapter(movies, this)
        addInfitePagination();
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                var moviePosition: Int = parent!!.getChildLayoutPosition(view)

                if (moviePosition == 0 || moviePosition == 1) {
                    outRect?.top = getAnIntDp(8)
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

    fun addInfitePagination() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollOutItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                isNextPage = true

                var currentItens: Int = layoutManager.getChildCount();
                var totalItems: Int = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                // Verifica se foi feito um scroll, se está no ultimo registro e
                // se ultima página de repósitorios não foi obtida
                if (isScrolling && (currentItens + scrollOutItems == totalItems) && !isLastPage) {
                    isScrolling = false;
                    // Obtem próxima página de reppsitorios
                    isNextPage = true
                    loadMovies()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == newState) {
                    isScrolling = true
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
        if (isNextPage) {
            showView(VISIBLE, GONE, GONE, VISIBLE)
        } else {
            showView(GONE, VISIBLE, GONE, GONE)
        }

        subscribe(movieViewModel.loadMovies(isNextPage, isGenericError)
        !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->
                    // Chamado no sucesso da obtenção dados
                    moviesAux.add(movie)
                }, {
                    // Chamado quando erro é apresentado
                    showError()
                }, {
                    // Chamado quando toda a chamada é completa
                    showMovies()
                })
        )
    }


    fun showView(recyclerViewVisibility: Int, progressVisibility: Int,
                 areaErroVisibility: Int, progressNextPageVisibility: Int) {
        recyclerView.visibility = recyclerViewVisibility
        progress.visibility = progressVisibility
        areaErro.visibility = areaErroVisibility
        progressNextPage.visibility = progressNextPageVisibility
    }

    fun showMovies() {
        isGenericError = false
        movies.addAll(moviesAux)
        showView(VISIBLE, GONE, GONE, GONE)
        recyclerView.adapter.notifyDataSetChanged()
    }

    fun showError() {
        isGenericError = true
        showView(GONE, GONE, VISIBLE, GONE)
    }

    fun reloadMovies(view: View) {
        loadMovies()
    }


}
