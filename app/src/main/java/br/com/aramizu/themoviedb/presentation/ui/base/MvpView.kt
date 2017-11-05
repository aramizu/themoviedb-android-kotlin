package br.com.aramizu.themoviedb.presentation.ui.base

import android.content.Context
import android.support.annotation.StringRes

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface MvpView {
    val isNetworkConnected: Boolean
    val screenContext: Context
    fun showLoading()
    fun hideLoading()
    fun onError(@StringRes resTitleId: Int, @StringRes resMessageId: Int)
    fun onError(title: String, message: String)
    fun hideKeyboard()
    fun setToolbarTitle(@StringRes resId: Int)
    fun setToolbarTitle(message: String)
    fun setToolbarStyle(toolbarStyle: Int)
    fun getString(@StringRes resId: Int): String
    fun getContext(): Context
}
