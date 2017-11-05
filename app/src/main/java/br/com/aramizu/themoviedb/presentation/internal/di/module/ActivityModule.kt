package br.com.aramizu.themoviedb.presentation.internal.di.module

import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import android.app.Activity
import android.content.Context
import dagger.Module
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchMvpView
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchPresenter
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchMvpPresenter
import br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying.NowPlayingMvpView
import br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying.NowPlayingPresenter
import br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying.NowPlayingMvpPresenter
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpView
import br.com.aramizu.themoviedb.presentation.ui.home.HomePresenter
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpPresenter
import javax.inject.Singleton
import br.com.aramizu.themoviedb.presentation.internal.di.ActivityContext
import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @PerActivity
    fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideNowPlayingPresenter(presenter: NowPlayingPresenter<NowPlayingMvpView>): NowPlayingMvpPresenter<NowPlayingMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideSearchPresenter(presenter: SearchPresenter<SearchMvpView>): SearchMvpPresenter<SearchMvpView> {
        return presenter
    }
}
