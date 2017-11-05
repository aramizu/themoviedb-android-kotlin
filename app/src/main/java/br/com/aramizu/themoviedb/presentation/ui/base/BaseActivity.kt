package br.com.aramizu.themoviedb.presentation.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.config.AndroidApplication
import br.com.aramizu.themoviedb.presentation.internal.di.components.ActivityComponent
import br.com.aramizu.themoviedb.presentation.internal.di.components.DaggerActivityComponent;
import br.com.aramizu.themoviedb.utils.CommonUtils
import br.com.aramizu.themoviedb.presentation.internal.di.module.ActivityModule
import br.com.tarefei.utils.NetworkUtils
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.app_toolbar.view.*

abstract open class BaseActivity : AppCompatActivity(), MvpView {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.toolbar_title)
    lateinit  var toolbarTitle: TextView

    private var progressDialog: ProgressDialog? = null

    val component: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(AndroidApplication.component)
                .build()
    }

    companion object {
        val NOW_PLAYING_STYLE = 0
        val SEARCH_STYLE = 1
        val DETAILS_STYLE = 2
    }

    /**
     * Check if device has any kind of network
     * @return
     */
    override val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    override val screenContext: Context
        get() = this

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this);
        setUpToolbar()
    }

    /**
     * Setup ActitonBar with the custom toolbar
     */
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    /**
     * Show loading on full screen to wait services
     */
    override fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    /**
     * Hide loading on full screen to wait services
     */
    override fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
        }
    }

    /**
     * Show error dialog.
     * @param resTitleId
     * @param resMessageId
     */
    override fun onError(@StringRes resTitleId: Int, @StringRes resMessageId: Int) {
        showDialog(getString(resTitleId), getString(resMessageId))
    }

    /**
     * Show error dialog.
     * @param title
     * @param message
     */
    override fun onError(title: String, message: String) {
        showDialog(title, message)
    }

    /**
     * Show informative dialog.
     * @param title
     * @param message
     */
    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
                .setPositiveButton("OK") { dialog, id -> dialog.dismiss() }

        if (!message.isEmpty())
            builder.setTitle(title)

        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Hide de SIP Keyboard
     */
    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Abstract method to setup after creation of a Activity or Fragment
     */
    protected abstract fun setUp()

    override fun setToolbarTitle(@StringRes resId: Int) {
        setToolbarTitle(getString(resId))
    }

    override fun setToolbarTitle(message: String) {
        toolbarTitle!!.text = message
    }

    /**
     * Change any component from toolbar (home button, color etc)
     * @param toolbarStyle
     */
    override fun setToolbarStyle(toolbarStyle: Int) {
        when (toolbarStyle) {
            NOW_PLAYING_STYLE -> {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setHomeButtonEnabled(false)
            }
            SEARCH_STYLE -> {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeButtonEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
            }
            DETAILS_STYLE -> {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeButtonEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
            }
        }
    }

    override fun getContext(): Context {
        return this
    }
}
