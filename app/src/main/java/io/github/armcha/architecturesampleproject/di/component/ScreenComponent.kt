package io.github.armcha.architecturesampleproject.di.component

import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.support.AndroidSupportInjectionModule
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule
import io.github.armcha.architecturesampleproject.di.scope.PerScreen


@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ScreenComponent
    }

    fun activityComponentBuilder(): ActivityComponent.Builder
}