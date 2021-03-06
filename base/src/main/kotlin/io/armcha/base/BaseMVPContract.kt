package io.armcha.base


import androidx.lifecycle.Lifecycle

interface BaseMVPContract {

    interface View

    interface Presenter<V : View> {

        val view: V?

        val innerLifecycle: Lifecycle?

        fun attachLifecycle(lifecycle: Lifecycle)

        fun detachLifecycle(lifecycle: Lifecycle)

        fun attachView(view: V)

        fun detachView()

        fun onPresenterCreate()

        fun onPresenterDestroy()
    }
}
