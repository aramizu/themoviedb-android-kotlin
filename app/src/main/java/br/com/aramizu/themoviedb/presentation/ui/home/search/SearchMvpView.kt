package br.com.aramizu.themoviedb.presentation.ui.home.search

import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.presentation.ui.base.MvpView

interface SearchMvpView : MvpView {
    fun showNowPlayingMovies(nowPlayingMovies: MoviesResponseModel)
}
