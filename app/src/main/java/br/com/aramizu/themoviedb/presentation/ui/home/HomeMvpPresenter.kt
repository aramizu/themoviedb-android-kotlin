package br.com.aramizu.themoviedb.presentation.ui.home

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity
import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter

@PerActivity
interface HomeMvpPresenter<V : HomeMvpView> : MvpPresenter<V> {
    fun clearMoviesFromPreferences()
}
