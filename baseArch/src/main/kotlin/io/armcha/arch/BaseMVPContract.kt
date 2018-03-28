package io.armcha.arch


import android.arch.lifecycle.Lifecycle
import android.os.Bundle

interface BaseMVPContract {

    interface View

    interface Presenter<V : View> {

        val view: V?

        fun attachLifecycle(lifecycle: Lifecycle)

        fun detachLifecycle(lifecycle: Lifecycle)

        fun attachView(view: V)

        fun detachView()

        fun onPresenterCreate()

        fun onPresenterDestroy()
    }
}
