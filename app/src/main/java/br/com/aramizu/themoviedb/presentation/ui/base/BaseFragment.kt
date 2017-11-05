package br.com.aramizu.themoviedb.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.Menu

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.presentation.internal.di.components.ActivityComponent
import br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying.NowPlayingFragment
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchFragment

abstract class BaseFragment : Fragment(), MvpView {

    var baseActivity: BaseActivity? = null
        private set

    /**
     * Check if device has any kind of network
     * @return
     */
    override val isNetworkConnected: Boolean
        get() {
            if (baseActivity != null) {
                return baseActivity!!.isNetworkConnected
            }
            onError(getString(R.string.dialog_title_error), getString(R.string.dialog_title_no_network_message))
            return false
        }

    val activityComponent: ActivityComponent
        get() = baseActivity!!.component

    override val screenContext: Context
        get() = baseActivity!!.screenContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.baseActivity = activity
        }
    }

    override fun onResume() {
        super.onResume()
        baseActivity!!.hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
        baseActivity!!.hideKeyboard()
    }

    override fun onDestroy() {
        onDetach()
        super.onDestroy()
    }

    /**
     * Show or hide menu overflow from toolbar starting from fragment
     * @param menu
     */
    override fun onPrepareOptionsMenu(menu: Menu?) {
        val item = menu!!.findItem(R.id.search)
        if (this is SearchFragment)
            item.isVisible = false
        if (this is NowPlayingFragment)
            item.isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    /**
     * Show loading on full screen to wait services
     */
    override fun showLoading() {
        if (baseActivity != null) {
            baseActivity!!.showLoading()
        }
    }

    /**
     * Hide loading on full screen to wait services
     */
    override fun hideLoading() {
        if (baseActivity != null) {
            baseActivity!!.hideLoading()
        }
    }

    /**
     * Show error dialog.
     * @param resId
     * @param type
     */
    override fun onError(@StringRes resId: Int, type: Int) {
        if (baseActivity != null) {
            baseActivity!!.onError(resId, type)
        }
    }

    /**
     * Show error dialog.
     * @param title
     * @param message
     */
    override fun onError(title: String, message: String) {
        if (baseActivity != null) {
            baseActivity!!.onError(title, message)
        }
    }

    /**
     * Hide de SIP Keyboard
     */
    override fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    /**
     * Detach the current fragment from his parent activity
     */
    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    /**
     * Abstract method to setup after creation of a Activity or Fragment
     */
    protected abstract fun setUp()

    override fun setToolbarTitle(@StringRes resId: Int) {
        baseActivity!!.setToolbarTitle(resId)
    }

    override fun setToolbarTitle(message: String) {
        baseActivity!!.setToolbarTitle(message)
    }

    /**
     * Change any component from toolbar (home button, color etc)
     * @param toolbarStyle
     */
    override fun setToolbarStyle(toolbarStyle: Int) {
        baseActivity!!.setToolbarStyle(toolbarStyle)
    }

    override fun getContext(): Context {
        return baseActivity!!.getContext()
    }
}
