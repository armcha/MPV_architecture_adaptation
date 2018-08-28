package io.armcha.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

abstract class BaseMVPActivity<V : BaseMVPContract.View, out P : BaseMVPContract.Presenter<V>>
    : AppCompatActivity(), BaseMVPContract.View, BaseViewModel.ClearCallBack {

    abstract val presenter: P

    private val delegate = ViewModelDelegate()

    abstract fun onStoredObjectReady(storedObject: Any?)
    abstract fun insertStoreObject(): Any?

    @Suppress("UNCHECKED_CAST")
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.create(this, ::insertStoreObject)
        onStoredObjectReady(delegate.storedObject)
        presenter.attachLifecycle(lifecycle)
        presenter.attachView(this as V)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        delegate.onResume(presenter)
    }

    @CallSuper
    override fun onDestroy() {
        presenter.detachLifecycle(lifecycle)
        presenter.detachView()
        super.onDestroy()
        delegate.onDestroy()
    }

    final override fun onCleared() {
        presenter.onPresenterDestroy()
    }
}

