package br.com.aramizu.themoviedb.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import br.com.aramizu.themoviedb.data.model.Movie
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext

class AppPreferencesHelper @Inject
internal constructor(@ApplicationContext context: Context) : PreferencesHelper {

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun setSerializableObject(keyValue: String, movies: List<Movie>) {
        val gson = Gson()
        val json = gson.toJson(movies)
        mPrefs.edit()
                .putString(keyValue, json)
                .apply()
    }

    private fun getSerializableMoviesList(keyValue: String): ArrayList<Movie> {
        val gson = Gson()
        val json = mPrefs.getString(keyValue, "")

        val type = object : TypeToken<List<Movie>>() {

        }.type

        if (json == null || (json != null && json.isEmpty()))
            return ArrayList<Movie>()
        else
            return gson.fromJson(json, type)
    }

    override fun saveMovies(movies: ArrayList<Movie>) {
        setSerializableObject(KEY_MOVIES, movies)
    }

    override fun retrieveMovies(): ArrayList<Movie> {
        return getSerializableMoviesList(KEY_MOVIES) as ArrayList<Movie>
    }

    override fun clearPreferences() {
        mPrefs.edit().clear().commit()
    }

    companion object {

        private val PREF_NAME = "THE_MOVIE_DB_PREFS"

        private val KEY_MOVIES = "KEY_MOVIES"
    }
}
