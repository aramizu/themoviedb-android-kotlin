package br.com.aramizu.themoviedb.presentation.ui.home.search

import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter
import javax.inject.Singleton

@Singleton
interface SearchMvpPresenter<V : SearchMvpView> : MvpPresenter<V> {
    fun getMoviesByTitle(title: String, page: Int)
}
