package br.com.aramizu.themoviedb.data.model

import java.io.Serializable

/**
 * Model Movie
 */
class Movie : Serializable {

    var isAdult: Boolean = false
    var backdrop_path: String? = null
    var genres: List<Long>? = null
    var id: Long = 0
    var original_language: String? = null
    var original_title: String? = null
    var overview: String? = null
    var release_date: String? = null
    var poster_path: String? = null
    var popularity: Double = 0.toDouble()
    var title: String? = null
    var isVideo: Boolean = false
    var vote_average: Double = 0.toDouble()
    var vote_count: Long = 0
    var media_type: String? = null
}
