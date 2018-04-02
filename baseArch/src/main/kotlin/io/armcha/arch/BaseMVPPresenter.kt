package io.armcha.arch


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.util.Log

/**
 * Created by Chatikyan on 20.05.2017.
 */

abstract class BaseMVPPresenter<V : BaseMVPContract.View> : LifecycleObserver, LifecycleOwner,
        BaseMVPContract.Presenter<V> {

    override var view: V? = null

    override var innerLifecycle: Lifecycle? = null

    override fun attachLifecycle(lifecycle: Lifecycle) {
        this.innerLifecycle = lifecycle
        lifecycle.addObserver(this)
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
        this.innerLifecycle = null
    }

    override fun getLifecycle(): Lifecycle {
        return innerLifecycle!!
    }

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onPresenterDestroy() {
        Log.e("onPresenterDestroy", "onPresenterDestroy ${this.javaClass.simpleName} with hashcode ${hashCode()}")
        //NO-OP
    }

    override fun onPresenterCreate() {
        Log.e("onPresenterCreate", "onPresenterCreate ${this.javaClass.simpleName} with hashcode ${hashCode()}")
        //NO-OP
    }
}
