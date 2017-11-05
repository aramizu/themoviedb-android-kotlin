package br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying

import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.presentation.ui.base.MvpView

interface NowPlayingMvpView : MvpView {
    fun showNowPlayingMovies(nowPlayingMovies: MoviesResponseModel)
}
