package br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying

import java.util.ArrayList

import javax.inject.Inject

import br.com.aramizu.themoviedb.data.DataManager
import br.com.aramizu.themoviedb.data.model.Movie
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.data.network.APIConstants
import br.com.aramizu.themoviedb.presentation.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NowPlayingPresenter<V : NowPlayingMvpView> @Inject
internal constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, compositeDisposable), NowPlayingMvpPresenter<V> {

    override val moviesFromPreference: ArrayList<Movie>
        get() = dataManager.retrieveMovies()

    override fun getNowPlayingMovies(averageVote: Double, page: Int) {
        if (page == APIConstants.INITIAL_PAGINATION_INDEX)
            mvpView!!.showLoading()

        compositeDisposable.add(dataManager.getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { nowPlayingMovies ->
                            val view = mvpView
                            view!!.hideLoading()

                            filterMoviesByAverageVote(averageVote, nowPlayingMovies)

                            mvpView!!.showNowPlayingMovies(nowPlayingMovies)
                        }
                ) { throwable ->
                    val view = mvpView
                    view!!.hideLoading()

                    handlerThrowableError(view, throwable)
                }
        )
    }

    override fun saveMoviesOnPreferences(results: ArrayList<Movie>) {
        val moviesOnPrefs: ArrayList<Movie> = ArrayList(moviesFromPreference)

        if (moviesOnPrefs.size > 0) {
            moviesOnPrefs.addAll(results)
            dataManager.saveMovies(moviesOnPrefs)
        } else {
            dataManager.saveMovies(results)
        }
    }

    /**
     * Filter movies from response to show only movies with average vote greater than 5.0 (averageVote)
     * @param averageVote
     * @param nowPlayingMovies
     */
    private fun filterMoviesByAverageVote(averageVote: Double, nowPlayingMovies: MoviesResponseModel) {
        val filteredMovies = ArrayList(nowPlayingMovies.results!!)

        for (movie in nowPlayingMovies.results!!) {
            if (movie.vote_average < averageVote) {
                filteredMovies.remove(movie)
            }
        }

        nowPlayingMovies.results = filteredMovies
    }
}
