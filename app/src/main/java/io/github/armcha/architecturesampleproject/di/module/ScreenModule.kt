package io.github.armcha.architecturesampleproject.di.module

import dagger.Module
import dagger.Provides
import io.github.armcha.architecturesampleproject.data.repository.SomeDataRepository
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository

@Module(subcomponents = [ActivityComponent::class])
class ScreenModule {

    @PerScreen
    @Provides
    fun provideSomeRepository(someDataRepository: SomeDataRepository): SomeRepository = someDataRepository
}