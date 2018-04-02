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

@Module
abstract class PresenterModule {

    @Binds
    abstract fun bindMainPresenter(mainActivityPresenter: MainActivityPresenter): MainActivityContract.Presenter

//    @Binds
//    abstract fun bindSecondPresenter(secActivityPresenter: SecondPresenter): SecondContract.Presenter

    @Binds
    abstract fun bindMainFragmentPresenter(mainFragmentPresenter: MainFragmentPresenter): MainFragmentContract.Presenter

    @Binds
    abstract fun bindSecondFragmentPresenter(secondFragmentPresenter: SecondFragmentPresenter): SecondFragmentContract.Presenter

}