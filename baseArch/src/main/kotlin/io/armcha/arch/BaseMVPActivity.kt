package io.armcha.arch

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

abstract class BaseMVPActivity<V : BaseMVPContract.View, P : BaseMVPContract.Presenter<V>>
    : AppCompatActivity(), BaseMVPContract.View {

    val presenterDelegate = PresenterDelegate<V, P>()

    // protected val presenter: P
    //    get() = presenterDelegate.presenter

    protected abstract fun initPresenter(): P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun createPresenter() {
        presenterDelegate.create(this, createPresenter = {
            initPresenter()
        })
    }

    fun createScreenComponent() {

    }

    fun releaseScreenComponent() {

    }

    @CallSuper
    override fun onDestroy() {
        presenterDelegate.destroy(lifecycle)
        super.onDestroy()
    }
}
