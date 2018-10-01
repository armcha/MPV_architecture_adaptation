package io.github.armcha.architecturesampleproject.di.module

import dagger.Binds
import dagger.Module
import io.github.armcha.architecturesampleproject.data.repository.EventDataRepository
import io.github.armcha.architecturesampleproject.data.repository.PhoneDataRepository
import io.github.armcha.architecturesampleproject.data.repository.SecondDataRepository
import io.github.armcha.architecturesampleproject.data.repository.SomeDataRepository
import io.github.armcha.architecturesampleproject.domain.repository.EventRepository
import io.github.armcha.architecturesampleproject.domain.repository.PhoneRepository
import io.github.armcha.architecturesampleproject.domain.repository.SecondRepository
import io.github.armcha.architecturesampleproject.domain.repository.SomeRepository

@Module
abstract class ScreenModule {

    @Binds
    abstract fun bindSomeRepository(someDataRepository: SomeDataRepository): SomeRepository

    @Binds
    abstract fun bindSecondRepository(secondDataRepository: SecondDataRepository): SecondRepository

    @Binds
    abstract fun bindEventRepository(eventDataRepository: EventDataRepository): EventRepository

    @Binds
    abstract fun bindPhoneRepository(PhoneDataRepository: PhoneDataRepository): PhoneRepository
}