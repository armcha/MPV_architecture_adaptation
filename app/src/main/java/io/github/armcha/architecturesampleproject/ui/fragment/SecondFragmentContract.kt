package io.github.armcha.architecturesampleproject.ui.fragment

import io.github.armcha.architecturesampleproject.ui.base.BaseContract


interface SecondFragmentContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}