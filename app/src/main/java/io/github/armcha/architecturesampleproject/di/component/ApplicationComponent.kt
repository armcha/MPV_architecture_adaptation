package io.github.armcha.architecturesampleproject.di.component

import dagger.Component
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    //operator fun plus(activityModule: ActivityModule): ActivityComponent

    operator fun plus(screenModule: ScreenModule): ScreenComponent

    fun inject(app: App)
}