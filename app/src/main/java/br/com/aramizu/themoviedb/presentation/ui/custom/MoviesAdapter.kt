package br.com.aramizu.themoviedb.presentation.ui.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.data.model.Movie
import br.com.aramizu.themoviedb.data.network.APIConstants
import br.com.aramizu.themoviedb.presentation.ui.details.MovieDetailsActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MoviesAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: MutableList<Movie>? = null
    private var onLoadMoreListenerInterface: OnLoadMoreListenerInterface? = null
    var currentPage: Int = 0
    private var totalPages: Int = 0

    init {
        this.currentPage = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val movieItem = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(movieItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = holder as MovieViewHolder

        val movie = movies!![position]

        if (movie.poster_path != null) {

            movieViewHolder.imageNotAvailable.visibility = View.GONE
            movieViewHolder.loading.visibility = View.VISIBLE
            Glide.with(context).load(String.format(APIConstants.POSTER_URL, movie.backdrop_path))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<String?, GlideDrawable?> {
                        override fun onException(e: Exception, model: String?, target: Target<GlideDrawable?>?, isFirstResource: Boolean): Boolean {
                            movieViewHolder.imageNotAvailable.visibility = View.VISIBLE
                            movieViewHolder.loading.visibility = View.GONE
                            return false
                        }
                        override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable?>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                            movieViewHolder.loading.visibility = View.GONE
                            return false
                        }
                    })
                    .bitmapTransform(RoundedCornersTransformation(context, 4, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(movieViewHolder.wallpaper)
        }

        movieViewHolder.wallpaper.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            val extra = Bundle()
            extra.putSerializable(MovieDetailsActivity.MOVIE_EXTRAS, movies!![position])
            intent.putExtras(extra)
            context.startActivity(intent)
        }

        movieViewHolder.title.text = movie.title
        movieViewHolder.grade.text = String.format("%.1f", movie.vote_average).toString()

        // Load more (pagination)
        if (currentPage < totalPages && position == movies!!.size - 1) {
            currentPage++
            onLoadMoreListenerInterface!!.onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return if (movies == null) 0 else movies!!.size
    }

    fun clearMovies() {
        if (this.movies != null && this.movies!!.size > 0) {
            this.movies!!.clear()
            currentPage = 1
        }
        notifyDataSetChanged()
    }

    fun addMovies(movies: MutableList<Movie>) {
        if (this.movies == null)
            this.movies = movies
        else {
            this.movies!!.addAll(movies)
        }
        notifyDataSetChanged()
    }

    fun setOnLoadMoreInterfaceListener(onLoadMoreInterfaceListener: OnLoadMoreListenerInterface) {
        this.onLoadMoreListenerInterface = onLoadMoreInterfaceListener
    }

    fun setTotalPages(totalPages: Int) {
        this.totalPages = totalPages
    }

    fun getMovies(): List<Movie>? {
        return movies
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var wallpaper: ImageView
        var title: TextView
        var grade: TextView
        var loading: ProgressBar
        var imageNotAvailable: TextView

        init {

            wallpaper = itemView.findViewById(R.id.wallpaper) as ImageView
            title = itemView.findViewById(R.id.title) as TextView
            grade = itemView.findViewById(R.id.grade) as TextView
            loading = itemView.findViewById(R.id.loading) as ProgressBar
            imageNotAvailable = itemView.findViewById(R.id.image_not_available) as TextView
        }
    }
}
