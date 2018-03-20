package io.github.armcha.architecturesampleproject.di.component

import dagger.Component
import io.github.armcha.architecturesampleproject.App
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ApplicationModule
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    operator fun plus(activityModule: ActivityModule): ActivityComponent

    fun inject(app: App)
}