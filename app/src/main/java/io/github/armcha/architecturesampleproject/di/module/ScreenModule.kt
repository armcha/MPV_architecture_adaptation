package io.github.armcha.architecturesampleproject.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.github.armcha.architecturesampleproject.data.repository.SecondDataRepository
import io.github.armcha.architecturesampleproject.data.repository.SomeDataRepository
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.repository.SecondRepository
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository

@Module
abstract class ScreenModule {

    @Binds
    abstract fun bindSomeRepository(someDataRepository: SomeDataRepository): SomeRepository

    @Binds
    abstract fun bindSecondRepository(secondDataRepository: SecondDataRepository): SecondRepository
}