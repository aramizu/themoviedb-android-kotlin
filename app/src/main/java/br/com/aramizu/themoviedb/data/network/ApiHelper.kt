package br.com.aramizu.themoviedb.data.network

import br.com.aramizu.themoviedb.data.model.ErrorResponse
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel
import io.reactivex.Observable
import retrofit2.Response

/**
 * Helper for Retrofit Api
 */
interface ApiHelper {
    fun getNowPlayingMovies(page: Int): Observable<MoviesResponseModel>
    fun getMoviesByTitle(query: String, page: Int): Observable<MoviesResponseModel>
    fun parseError(response: Response<*>): ErrorResponse?
}
