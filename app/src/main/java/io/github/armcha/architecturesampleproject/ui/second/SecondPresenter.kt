package io.github.armcha.architecturesampleproject.ui.second

import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import io.github.armcha.architecturesampleproject.domain.interactor.SomeInteractor
import javax.inject.Inject

@PerScreen
class SecondPresenter @Inject constructor(private val someInteractor: SomeInteractor)
    : BasePresenter<SecondContract.View>(), SecondContract.Presenter {

}