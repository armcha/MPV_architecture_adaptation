package io.github.armcha.architecturesampleproject.ui.base

import io.armcha.base.BaseMVPContract

interface BaseContract {

    interface View : BaseMVPContract.View

    interface Presenter<V : View> : BaseMVPContract.Presenter<V>
}