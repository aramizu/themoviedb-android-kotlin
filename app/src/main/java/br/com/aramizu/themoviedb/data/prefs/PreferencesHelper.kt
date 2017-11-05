package br.com.aramizu.themoviedb.data.prefs;

import br.com.aramizu.themoviedb.data.model.Movie

interface PreferencesHelper {
    fun saveMovies(movies: ArrayList<Movie>)
    fun retrieveMovies(): ArrayList<Movie>
    fun clearPreferences()
}
