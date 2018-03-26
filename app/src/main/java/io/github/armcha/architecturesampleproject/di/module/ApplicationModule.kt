package io.github.armcha.architecturesampleproject.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import io.github.armcha.architecturesampleproject.di.component.ScreenComponent
import io.github.armcha.architecturesampleproject.di.qualifier.IoScheduler
import io.github.armcha.architecturesampleproject.di.qualifier.UIScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    @IoScheduler
    fun provideIOScheduler() = Schedulers.io()

    @Singleton
    @Provides
    @UIScheduler
    fun provideUIScheduler(): Scheduler = AndroidSchedulers.mainThread()
}