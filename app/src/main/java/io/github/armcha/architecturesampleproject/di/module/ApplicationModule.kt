package io.github.armcha.architecturesampleproject.di.module

import dagger.Module
import dagger.Provides
import io.github.armcha.architecturesampleproject.di.qualifier.BgContext
import io.github.armcha.architecturesampleproject.di.qualifier.IoScheduler
import io.github.armcha.architecturesampleproject.di.qualifier.UIContext
import io.github.armcha.architecturesampleproject.di.qualifier.UIScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    @Singleton
    @Provides
    @BgContext
    fun provideBgContext(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @Provides
    @UIContext
    fun provideUIContext(): CoroutineDispatcher = Dispatchers.Main
}