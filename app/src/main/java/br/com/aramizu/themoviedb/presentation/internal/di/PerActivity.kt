package br.com.aramizu.themoviedb.presentation.internal.di

import kotlin.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity

