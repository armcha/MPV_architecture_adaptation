package io.github.armcha.architecturesampleproject.di.component

import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.di.module.PresenterModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule
import io.github.armcha.architecturesampleproject.di.scope.PerScreen


@PerScreen
@Subcomponent(modules = [ScreenModule::class, PresenterModule::class])
interface ScreenComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ScreenComponent
    }

    fun activityComponentBuilder(): ActivityComponent.Builder
}