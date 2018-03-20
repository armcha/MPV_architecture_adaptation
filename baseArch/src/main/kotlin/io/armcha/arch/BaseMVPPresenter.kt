package io.armcha.arch


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.annotation.CallSuper

import org.jetbrains.annotations.Contract

/**
 * Created by Chatikyan on 20.05.2017.
 */

abstract class BaseMVPPresenter<V : BaseMVPContract.View> : LifecycleObserver, BaseMVPContract.Presenter<V> {

    override var view: V? = null

    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onPresenterDestroy() {
        //NO-OP
    }

    override fun onPresenterCreate() {
        //NO-OP
    }
}
