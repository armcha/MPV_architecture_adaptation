package io.github.armcha.architecturesampleproject.ui.fragment

import io.github.armcha.architecturesampleproject.ui.base.BaseContract


interface MainFragmentContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}