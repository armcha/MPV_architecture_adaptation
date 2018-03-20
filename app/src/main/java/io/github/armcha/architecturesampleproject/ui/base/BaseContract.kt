package io.github.armcha.architecturesampleproject.ui.base

import io.armcha.arch.BaseMVPContract

interface BaseContract {

    interface View : BaseMVPContract.View {


    }

    interface Presenter<V : BaseMVPContract.View> : BaseMVPContract.Presenter<V> {


    }
}