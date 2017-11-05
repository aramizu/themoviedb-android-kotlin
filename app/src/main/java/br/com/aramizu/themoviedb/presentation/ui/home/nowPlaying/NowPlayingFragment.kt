package br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import javax.inject.Inject

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity
import br.com.aramizu.themoviedb.presentation.ui.base.BaseFragment
import br.com.aramizu.themoviedb.presentation.ui.custom.MoviesAdapter
import br.com.aramizu.themoviedb.presentation.ui.custom.OnLoadMoreListenerInterface
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity
import butterknife.BindView
import butterknife.ButterKnife

class NowPlayingFragment : BaseFragment(), NowPlayingMvpView {

    @BindView(R.id.lst_movies)
    lateinit var lstMovies: RecyclerView

    @Inject lateinit var presenter: NowPlayingMvpPresenter<NowPlayingMvpView>

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var mParentActivity: HomeActivity

    companion object {

        private val AVERAGE_VOTE = 5.0

        fun newInstance(): NowPlayingFragment {
            val args = Bundle()
            val fragment = NowPlayingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mParentActivity = baseActivity as HomeActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activityComponent.inject(this)
        presenter.onAttach(this)

        val view = inflater!!.inflate(R.layout.fragment_now_playing, container, false)

        ButterKnife.bind(this, view)

        setUp()

        return view
    }

    override fun setUp() {
        setToolbarTitle(R.string.now_playing_title)
        setToolbarStyle(BaseActivity.NOW_PLAYING_STYLE)

        moviesAdapter = MoviesAdapter(mParentActivity)

        moviesAdapter.setOnLoadMoreInterfaceListener(object : OnLoadMoreListenerInterface {
            override fun onLoadMore() {
                if (isNetworkConnected)
                    presenter.getNowPlayingMovies(AVERAGE_VOTE, moviesAdapter.currentPage)
            }
        })

        lstMovies.adapter = moviesAdapter
        lstMovies.itemAnimator = DefaultItemAnimator()
        lstMovies.layoutManager = LinearLayoutManager(mParentActivity)
    }

    override fun onResume() {
        super.onResume()

        // Get the movies list from cache
        val movies = presenter.moviesFromPreference

        if (movies.size > 0)
            moviesAdapter.addMovies(movies)
        else {
            moviesAdapter.clearMovies()
            if (isNetworkConnected)
                presenter.getNowPlayingMovies(AVERAGE_VOTE, moviesAdapter.currentPage)
        }
    }

    override fun showNowPlayingMovies(nowPlayingMovies: MoviesResponseModel) {
        moviesAdapter.addMovies(nowPlayingMovies.results!!)
        moviesAdapter.currentPage = nowPlayingMovies.page
        moviesAdapter.setTotalPages(nowPlayingMovies.total_pages)
        presenter.saveMoviesOnPreferences(nowPlayingMovies.results!!)
    }
}
