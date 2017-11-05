package br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying

import br.com.aramizu.themoviedb.data.model.Movie
import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity
import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter
import javax.inject.Singleton

@PerActivity
interface NowPlayingMvpPresenter<V : NowPlayingMvpView> : MvpPresenter<V> {
    val moviesFromPreference: ArrayList<Movie>
    fun getNowPlayingMovies(averageVote: Double, page: Int)
    fun saveMoviesOnPreferences(results: ArrayList<Movie>)
}
