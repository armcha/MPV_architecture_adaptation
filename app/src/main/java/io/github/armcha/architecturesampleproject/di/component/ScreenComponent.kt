package io.github.armcha.architecturesampleproject.di.component

import dagger.Subcomponent
import io.github.armcha.architecturesampleproject.di.module.ActivityModule
import io.github.armcha.architecturesampleproject.di.module.ScreenModule
import io.github.armcha.architecturesampleproject.di.scope.PerScreen


@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    operator fun plus(activityModule: ActivityModule): ActivityComponent
}