package br.com.aramizu.themoviedb.data.network

/**
 * Some constants used by network context
 */
object APIConstants {

    /** API KEY  */
    val API_KEY = "d272326e467344029e68e3c4ff0b4059"

    /** Server endpoint.  */
    val IMAGE_SERVER_URL = "https://image.tmdb.org/t/p/"

    /** Poster size.  */
    val POSTER_SIZE = "w500"

    /** Movies services.  */
    val POSTER_URL = "$IMAGE_SERVER_URL$POSTER_SIZE%1\$s"

    /** Initial pagination index for response movies on a list.  */
    val INITIAL_PAGINATION_INDEX = 1
}
