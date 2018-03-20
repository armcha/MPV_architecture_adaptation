package io.armcha.arch

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class BaseMVPActivity<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : AppCompatActivity(), BaseMVPContract.View{

    private val presenterDelegate = PresenterDelegate<V, P>()

    protected val presenter: P
        get() = presenterDelegate.presenter

    protected abstract fun initPresenter(): P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterDelegate.create(this, createPresenter = {
            Log.e("initPresenter", "initPresenter CALLED from ${this.javaClass.simpleName}")
            initPresenter()
        })
    }

    @CallSuper
    override fun onDestroy() {
        presenterDelegate.destroy(lifecycle)
        super.onDestroy()
    }
}
