package br.com.aramizu.themoviedb.presentation.ui.home.search

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView

import org.w3c.dom.Text

import javax.inject.Inject

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity
import br.com.aramizu.themoviedb.presentation.ui.base.BaseFragment
import br.com.aramizu.themoviedb.presentation.ui.custom.MoviesAdapter
import br.com.aramizu.themoviedb.presentation.ui.custom.OnLoadMoreListenerInterface
import br.com.aramizu.themoviedb.presentation.ui.custom.dialogs.GenericDialogOkCancel
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import org.jetbrains.annotations.NotNull

class SearchFragment : BaseFragment(), SearchMvpView {

    @BindView(R.id.edt_search)
    lateinit var edtSearch: EditText
    @BindView(R.id.lst_movies)
    lateinit var lstMovies: RecyclerView

    @Inject lateinit var presenter: SearchMvpPresenter<SearchMvpView>

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var mParentActivity: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mParentActivity = baseActivity as HomeActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        presenter.onAttach(this)

        val view = inflater!!.inflate(R.layout.fragment_search, container, false)

        ButterKnife.bind(this, view)

        setUp()

        return view
    }

    override fun setUp() {
        setToolbarTitle(R.string.search_title)
        setToolbarStyle(BaseActivity.SEARCH_STYLE)

        moviesAdapter = MoviesAdapter(mParentActivity)

        moviesAdapter.setOnLoadMoreInterfaceListener(object : OnLoadMoreListenerInterface {
            override fun onLoadMore() {
                if (isNetworkConnected)
                    presenter.getMoviesByTitle(edtSearch.text.toString(), moviesAdapter.currentPage)
            }
        })

        lstMovies.adapter = moviesAdapter
        lstMovies.itemAnimator = DefaultItemAnimator()
        lstMovies.layoutManager = LinearLayoutManager(mParentActivity)
    }

    override fun showNowPlayingMovies(nowPlayingMovies: MoviesResponseModel) {
        if (nowPlayingMovies.results!!.size > 0) {
            hideKeyboard()
            moviesAdapter.addMovies(nowPlayingMovies.results!!)
            moviesAdapter.currentPage = nowPlayingMovies.page
            moviesAdapter.setTotalPages(nowPlayingMovies.total_pages)
        } else {
            GenericDialogOkCancel(
                    context,
                    mParentActivity!!.getString(R.string.dialog_title_error),
                    mParentActivity!!.getString(R.string.dialog_title_no_results_message),
                    mParentActivity!!.getString(R.string.dialog_ok_label),
                    null
            ).showDialog()
        }
    }

    @OnClick(R.id.search)
    fun onSearchClick() {
        if (verifyFields() && isNetworkConnected) {
            moviesAdapter.clearMovies()
            presenter.getMoviesByTitle(edtSearch.text.toString(), moviesAdapter.currentPage)
        }
    }

    private fun verifyFields(): Boolean {
        if (edtSearch.text.toString().trim { it <= ' ' }.isEmpty()) {
            GenericDialogOkCancel(
                    context,
                    mParentActivity!!.getString(R.string.dialog_title_error),
                    mParentActivity!!.getString(R.string.dialog_title_mandatory_search_filed_message),
                    mParentActivity!!.getString(R.string.dialog_ok_label), null
            ).showDialog()
            return false
        }
        return true
    }

    companion object {

        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
