package br.com.aramizu.themoviedb.presentation.internal.di.components

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity
import br.com.aramizu.themoviedb.presentation.internal.di.module.ActivityModule
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity
import br.com.aramizu.themoviedb.presentation.ui.home.nowPlaying.NowPlayingFragment
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchFragment
import dagger.Component


@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: HomeActivity)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: NowPlayingFragment)
}