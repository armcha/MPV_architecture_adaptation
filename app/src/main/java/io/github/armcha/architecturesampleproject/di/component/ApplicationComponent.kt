package io.github.armcha.architecturesampleproject.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun screenBuilder(): ScreenComponent.Builder

    fun inject(app: App)
}