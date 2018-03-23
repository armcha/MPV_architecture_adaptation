package io.github.armcha.architecturesampleproject.ui.second

import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SecondPresenter @Inject constructor()
    : BasePresenter<SecondContract.View>(), SecondContract.Presenter {

}