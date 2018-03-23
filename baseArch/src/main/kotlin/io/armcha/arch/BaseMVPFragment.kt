package io.armcha.arch

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseMVPFragment<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : Fragment(), BaseMVPContract.View {

    private val presenterDelegate = PresenterDelegate<V, P>()

    protected abstract fun initPresenter(): P

    fun createPresenter(){
        presenterDelegate.create(this, createPresenter = {
            initPresenter()
        })
    }

    @CallSuper
    override fun onDestroyView() {
        presenterDelegate.destroy(lifecycle)
        super.onDestroyView()
    }

}
