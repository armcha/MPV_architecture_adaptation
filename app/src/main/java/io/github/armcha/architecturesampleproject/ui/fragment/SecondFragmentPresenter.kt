package io.github.armcha.architecturesampleproject.ui.fragment

import io.github.armcha.architecturesampleproject.ui.base.BasePresenter
import io.github.armcha.architecturesampleproject.di.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SecondFragmentPresenter @Inject constructor()
    : BasePresenter<SecondFragmentContract.View>(), SecondFragmentContract.Presenter {



}