package io.github.armcha.architecturesampleproject.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule
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

    //fun screenComp():ScreenComponent

    operator fun plus(screenModule: ScreenModule): ScreenComponent

    //fun inject(app: App)
}