package br.com.aramizu.themoviedb.presentation.ui.details

import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ProgressBar
import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.data.network.APIConstants
import br.com.aramizu.themoviedb.data.model.Movie
import butterknife.BindView


class MovieDetailsActivity : BaseActivity() {

    companion object {
        val MOVIE_EXTRAS = "MOVIE_EXTRAS"
    }

    @BindView(R.id.title_header)
    lateinit var titleHeader: TextView
    @BindView(R.id.grade)
    lateinit var grade: TextView
    @BindView(R.id.cover)
    lateinit var cover: ImageView
    @BindView(R.id.cover_loading)
    lateinit var coverLoading: ProgressBar
    @BindView(R.id.wallpaper)
    lateinit var wallpaper: ImageView
    @BindView(R.id.wallpaper_loading)
    lateinit var wallpaperLoading: ProgressBar
    @BindView(R.id.image_not_available)
    lateinit var imgNotAvailable: TextView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.release_date)
    lateinit var releaseDate: TextView
    @BindView(R.id.overview)
    lateinit var overview: TextView

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movie = intent.extras!!.getSerializable(MOVIE_EXTRAS) as Movie

        setUp()
    }

    override fun setUp() {
        setToolbarTitle(R.string.details_title)
        setToolbarStyle(BaseActivity.DETAILS_STYLE)

        titleHeader.setText(movie!!.title)
        grade.setText(String.format("%.1f", movie!!.vote_average).toString())
        title.setText(movie!!.original_language)
        releaseDate.setText(movie!!.release_date)
        overview.setText(movie!!.overview)

        Glide.with(this).load(String.format(APIConstants.POSTER_URL, movie!!.backdrop_path))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<String?, GlideDrawable?> {
                    override fun onException(e: Exception, model: String?, target: Target<GlideDrawable?>?, isFirstResource: Boolean): Boolean {
                        imgNotAvailable.visibility = View.VISIBLE
                        wallpaperLoading.visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable?>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        wallpaperLoading.visibility = View.GONE
                        return false
                    }
                })
                .bitmapTransform(RoundedCornersTransformation(this, 4, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(wallpaper)

        Glide.with(this).load(String.format(APIConstants.POSTER_URL, movie!!.poster_path))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<String?, GlideDrawable?> {
                    override fun onException(e: Exception, model: String?, target: Target<GlideDrawable?>?, isFirstResource: Boolean): Boolean {
                        coverLoading.visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable?>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        coverLoading.visibility = View.GONE
                        return false
                    }
                })
                .bitmapTransform(RoundedCornersTransformation(this, 4, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(cover)
    }
}