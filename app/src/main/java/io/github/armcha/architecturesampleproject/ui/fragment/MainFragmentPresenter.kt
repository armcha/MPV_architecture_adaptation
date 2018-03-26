package io.github.armcha.architecturesampleproject.ui.fragment

import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainFragmentPresenter @Inject constructor()
    : BasePresenter<MainFragmentContract.View>(), MainFragmentContract.Presenter {



}