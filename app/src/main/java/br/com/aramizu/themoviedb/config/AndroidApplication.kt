package br.com.aramizu.themoviedb.config

import android.app.Application
import android.content.Context
import br.com.aramizu.themoviedb.presentation.internal.di.components.ApplicationComponent
import br.com.aramizu.themoviedb.presentation.internal.di.components.DaggerApplicationComponent
import br.com.aramizu.themoviedb.presentation.internal.di.module.ApplicationModule

class AndroidApplication: Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        component.inject(this)
    }
}