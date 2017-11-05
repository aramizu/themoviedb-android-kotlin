package br.com.aramizu.themoviedb.presentation.ui.home

import br.com.aramizu.themoviedb.data.DataManager
import br.com.aramizu.themoviedb.presentation.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView> @Inject
internal constructor(dataManager: DataManager, compositeDisposable: CompositeDisposable) : BasePresenter<V>(dataManager, compositeDisposable), HomeMvpPresenter<V> {

    override fun clearMoviesFromPreferences() {
        dataManager.clearPreferences()
    }
}
