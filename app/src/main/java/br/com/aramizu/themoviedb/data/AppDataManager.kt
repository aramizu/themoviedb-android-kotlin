package br.com.aramizu.themoviedb.data

import android.content.Context

import javax.inject.Inject

import br.com.aramizu.themoviedb.data.model.ErrorResponse
import br.com.aramizu.themoviedb.data.model.Movie
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import br.com.aramizu.themoviedb.data.network.ApiHelper
import br.com.aramizu.themoviedb.data.prefs.PreferencesHelper
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext
import io.reactivex.Observable
import retrofit2.Response

/**
 * Implementation of manager that implements all forms of data application (network, android preferences)
 */
class AppDataManager @Inject
internal constructor(@param:ApplicationContext private val mContext: Context,
                     private val mPreferencesHelper: PreferencesHelper,
                     private val mApiHelper: ApiHelper) : DataManager {

    override fun getNowPlayingMovies(page: Int): Observable<MoviesResponseModel> {
        return mApiHelper.getNowPlayingMovies(page)
    }

    override fun getMoviesByTitle(query: String, page: Int): Observable<MoviesResponseModel> {
        return mApiHelper.getMoviesByTitle(query, page)
    }

    override fun parseError(response: Response<*>): ErrorResponse? {
        return mApiHelper.parseError(response)
    }

    override fun saveMovies(movies: ArrayList<Movie>) {
        mPreferencesHelper.saveMovies(movies)
    }

    override fun retrieveMovies(): ArrayList<Movie> {
        return mPreferencesHelper.retrieveMovies()
    }

    override fun clearPreferences() {
        mPreferencesHelper.clearPreferences()
    }
}
