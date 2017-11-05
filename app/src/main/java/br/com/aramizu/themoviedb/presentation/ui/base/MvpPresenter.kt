package br.com.aramizu.themoviedb.presentation.ui.base

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface MvpPresenter<V : MvpView> {
    fun onAttach(mvpView: V)
    fun onDetach()
}
