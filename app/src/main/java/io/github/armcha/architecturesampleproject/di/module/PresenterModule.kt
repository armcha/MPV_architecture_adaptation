package io.github.armcha.architecturesampleproject.di.module

import dagger.Binds
import dagger.Module
import io.github.armcha.architecturesampleproject.di.component.ActivityComponent
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.ui.fragment.MainFragmentContract
import io.github.armcha.architecturesampleproject.ui.fragment.MainFragmentPresenter
import io.github.armcha.architecturesampleproject.ui.fragment.SecondFragmentContract
import io.github.armcha.architecturesampleproject.ui.fragment.SecondFragmentPresenter
import io.github.armcha.architecturesampleproject.ui.main.MainActivityContract
import io.github.armcha.architecturesampleproject.ui.main.MainActivityPresenter
import io.github.armcha.architecturesampleproject.ui.second.SecondContract
import io.github.armcha.architecturesampleproject.ui.second.SecondPresenter

@Module(subcomponents = [ActivityComponent::class])
abstract class PresenterModule {

    @PerScreen
    @Binds
    abstract fun bindMainPresenter(mainActivityPresenter: MainActivityPresenter): MainActivityContract.Presenter

    @PerScreen
    @Binds
    abstract fun bindSecondPresenter(secActivityPresenter: SecondPresenter): SecondContract.Presenter

    @PerScreen
    @Binds
    abstract fun bindMainFragmentPresenter(mainFragmentPresenter: MainFragmentPresenter): MainFragmentContract.Presenter

    @PerScreen
    @Binds
    abstract fun bindSecondFragmentPresenter(secondFragmentPresenter: SecondFragmentPresenter): SecondFragmentContract.Presenter

}