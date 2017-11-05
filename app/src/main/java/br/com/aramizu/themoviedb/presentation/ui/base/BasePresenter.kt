package br.com.aramizu.themoviedb.presentation.ui.base

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

import javax.inject.Inject

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.data.DataManager
import br.com.aramizu.themoviedb.data.model.ErrorResponse
import br.com.aramizu.themoviedb.presentation.ui.custom.dialogs.GenericDialogOkCancel
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : MvpView> @Inject
constructor(val dataManager: DataManager, val compositeDisposable: CompositeDisposable) : MvpPresenter<V> {

    var mvpView: V? = null
        private set

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        mvpView = null
    }

    /**
     * Handle the network error to show a returned message or a generic error message
     * @param view
     * @param throwable
     */
    fun handlerThrowableError(view: V, throwable: Throwable) = if (throwable is HttpException) {
        val error = dataManager.parseError(throwable.response())
        if (error != null) {
            handleHttpExceptionWithErrorResponse(error)
        } else {
            view.onError(R.string.dialog_title_error, R.string.dialog_title_generic_message)
        }
    } else {
        view.onError(R.string.dialog_title_error, R.string.dialog_title_generic_message)
    }

    /**
     * Handle the network error to show a returned message
     * @param error
     */
    fun handleHttpExceptionWithErrorResponse(error: ErrorResponse) {
        val dialog = GenericDialogOkCancel(
                mvpView!!.getContext(), null,
                error.statusMessage!!, null,
                "OK"
        )
        dialog.showDialog()
    }
}
