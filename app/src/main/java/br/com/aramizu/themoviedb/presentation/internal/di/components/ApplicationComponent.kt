package br.com.aramizu.themoviedb.presentation.internal.di.components

import android.app.Application
import android.content.Context
import br.com.aramizu.themoviedb.config.AndroidApplication
import br.com.aramizu.themoviedb.data.DataManager
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext
import br.com.aramizu.themoviedb.presentation.internal.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(app: AndroidApplication)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun getDataManager(): DataManager
}
